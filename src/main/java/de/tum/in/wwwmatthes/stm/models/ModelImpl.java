package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.annotation.Nonnull;

import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.nd4j.linalg.primitives.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.tum.in.wwwmatthes.stm.examples.RankExample;
import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigFactory;
import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;
import de.tum.in.wwwmatthes.stm.tokenizers.CustomTokenizerFactory;

abstract class ModelImpl implements Model {
		
	private Map<String, String> documentsContentLookupTable				= new HashMap<String, String>(); // Only for debugging
	private Map<String, INDArray> documentsLookupTable 					= new HashMap<String, INDArray>();
	
	// Variables
	protected CustomTokenizerFactory 	tokenizerFactory;
	protected Config 					config;
	
	protected static Logger log = LoggerFactory.getLogger(ModelImpl.class);
	
	ModelImpl(Config config) {
		super();
		
		// Set Config
		this.config = config;
		
		// Tokenizer Factory - Init
		CustomTokenizerFactory tokenizerFactory = new CustomTokenizerFactory();
		tokenizerFactory.setPreprocessingEnabled(config.isPreprocessingEnabled());
		tokenizerFactory.setStemmingEnabled(config.isStemmingEnabled());
		tokenizerFactory.setAllowedPosTags(config.getAllowedPosTags());
		
		this.tokenizerFactory = tokenizerFactory;
	}

	/**
	 * Trains the model.
	 */
	public abstract void fit() throws VocabularyMatchException;
	
	/**
	 * Converts a text to a vector.
	 * 
	 * @param  text Text to convert.         
	 * @return vector Vector converted from text.
	 */
	public abstract INDArray vectorFromText(String text) throws VocabularyMatchException;
		
	/**
	 * Calculates the similarity between two vectors.
	 * 
	 * @param  vector1 Vector. 
	 * @param  vector2 Vector to compare.         
	 * @return similarity Similarity between two vectors.
	 */
	public double similarity(INDArray vector1, INDArray vector2) {
		if (vector1 != null && vector2 != null) {
			return Transforms.cosineSim(vector1, vector2);
		}
		return -1;
	}
	
	/**
	 * Calculates the similarity between a text and a label.
	 * 
	 * @param  label Label to compare. 
	 * @param  text Text to compare.         
	 * @return similarity Similarity between text and label.
	 */
	public double similarity(String text, String label) throws VocabularyMatchException {
		INDArray vector1 = vectorFromText(text);
		INDArray vector2 = documentsLookupTable.get(label);
		return similarity(vector1, vector2);
	}
	
	/**
	 * Calculates the similarities between a text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return similarities List of labels and their similarities.
	 */
	public List<Pair<String, Double>> rankedDocumentsWithSimilaritiesForText(String text) throws VocabularyMatchException {
		INDArray vector = vectorFromText(text);
		
		if(vector != null && vector.amaxNumber().doubleValue() != 0) {
			List<Pair<String, Double>> similarDocs 	= new ArrayList<Pair<String, Double>>();
			
			// Add
			for(Entry<String, INDArray> entry : documentsLookupTable.entrySet()) {	
				Double similarity = similarity(vector, entry.getValue());
				similarDocs.add(new Pair<String, Double>(entry.getKey(), similarity));
			}
			
			// Sort 
			Collections.sort(similarDocs, new Comparator<Pair<String, Double>>() {
				public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
					return ModelImpl.compare(o1.getValue(), o2.getValue());
				}
			});
			
			return similarDocs;
		}
		
		throw new VocabularyMatchException(text);
	}
	
	/**
	 * Calculates the similarities between a text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return similarities List of labels and their similarities.
	 */
	public List<String> rankedDocumentsForText(String text) throws VocabularyMatchException {

		List<Pair<String, Double>> similarDocs = rankedDocumentsWithSimilaritiesForText(text);
		if (similarDocs != null) {
			List<String> list = new ArrayList<String>();
			for(Pair<String, Double> pair : similarDocs) {	
				list.add(pair.getKey());
			}		
			return list;
		}
		
		return null;
	}
	
	@Override
	public String processString(String text) {
		String preprocessedString = tokenizerFactory.processString(text);
		
		List<String> result		 	= new ArrayList<String>();
		StringTokenizer tokenizer	= new StringTokenizer(preprocessedString);
		
		while(tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if(!StopWords.getStopWords().contains(token)){
				result.add(token);
	        }
		}

		return String.join(" ", result);
	}
	
	/**
	 * Returns the content for the given document label.
	 * 
	 * @param label Label of the document which content was requested.
	 * @return content Content of the document 
	 */
	public String getContentForDocument(String label) {
		return documentsContentLookupTable.get(label);
	}
		
	public void putDocuments(LabelAwareIterator documentsLabelAwareIterator) throws VocabularyMatchException {
		Map<String, String> documents = new HashMap<String, String>();
		
		// Iterate
		while(documentsLabelAwareIterator.hasNext()) {
			LabelledDocument 	labelledDocument 		= documentsLabelAwareIterator.nextDocument();
			String 				labelledDocumentId 		= labelledDocument.getLabels().get(0);
			documents.put(labelledDocumentId, labelledDocument.getContent());
		}
		
		putDocuments(documents);
	}
	
	public void putDocuments(Map<String, String> documents) throws VocabularyMatchException {
		
		for(String key: documents.keySet()) {
			putDocument(key, documents.get(key));
		}
		
	}
	
	private void putDocument(String id, String content) throws VocabularyMatchException {
		if (id!=null && content!=null) {
			try {
				INDArray labelledDocumentVector = vectorFromText(content);
				documentsLookupTable.put(id, labelledDocumentVector);
				documentsContentLookupTable.put(id, content);	
			} catch (VocabularyMatchException e) {
				throw new VocabularyMatchException(content);
			}
		} else {
			log.error("Parameters may not be null. (id=" + id + ", content " + content + ")");
		}
	}
			
	@Override
	public Map<String, String> getDocumentContents() {
		return documentsContentLookupTable;
	}
	
	@Override
	public void write(File directory, String identifier) throws IOException {
				
		// Save Config
		config.writeToFile(getConfigFile(directory, identifier));
		
		// Save Model
		writeModel(getModelFile(directory, identifier));
	}
	
	protected abstract void writeModel(File file);
	
	protected void read(File file) throws Exception {
		String identifier	= FilenameUtils.getBaseName(file.getAbsolutePath());
		File directory 		= new File(FilenameUtils.getFullPath(file.getAbsolutePath()));
				
		Config config 		= ConfigFactory.buildFromFile(getConfigFile(directory, identifier));
		File modelFile 		= getModelFile(directory, identifier);

		readModel(modelFile, config);
	}
	
	protected abstract void readModel(File modelFile, Config config) throws Exception;
	
	// Private Functions
	
	private static int compare(Double o1, Double o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return -1;
        }
        return o2.compareTo(o1);
	}
	
	static File getConfigFile(File directory, String identifier) {
		return new File(directory, identifier + ".config");
	}
	
	static File getConfigFileFromModeFile(File file) {
		String identifier	= FilenameUtils.getBaseName(file.getAbsolutePath());
		File directory 		= new File(FilenameUtils.getFullPath(file.getAbsolutePath()));
		
		return getConfigFile(directory, identifier);
	}
	
	static File getModelFile(File directory, String identifier) {
		return new File(directory, identifier + ".model");
	}
	
}
