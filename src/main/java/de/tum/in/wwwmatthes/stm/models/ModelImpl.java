package de.tum.in.wwwmatthes.stm.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.nd4j.linalg.primitives.Pair;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.tokenizers.CustomTokenizerFactory;

abstract class ModelImpl implements Model {
		
	private Map<String, String> documentsContentLookupTable = new HashMap<String, String>(); // Only for debugging
	private Map<String, INDArray> documentsLookupTable = new HashMap<String, INDArray>();
	
	// Variables
	protected LabelAwareIterator 	documentsLabelAwareIterator;
	protected CustomTokenizerFactory 	tokenizerFactory;
	protected VocabCache<VocabWord>	vocab;
	
	ModelImpl(Config config) {
		super();
		
		System.out.println("Config:");
		System.out.println(config);
				
		// Documents Label Aware Iterator
		documentsLabelAwareIterator = new FileLabelAwareIterator.Builder()
	              .addSourceFolder(config.getDocumentsSourceFile())
	              .build();
		
		// Tokenizer Factory
		CustomTokenizerFactory tokenizerFactory = new CustomTokenizerFactory();
		tokenizerFactory.setPreprocessingEnabled(config.isPreprocessingEnabled());
		tokenizerFactory.setStemmingEnabled(config.isStemmingEnabled());
		tokenizerFactory.setAllowedPosTags(config.getAllowedPosTags());
		
		this.tokenizerFactory = tokenizerFactory;
	}

	/**
	 * Trains the model.
	 */
	public abstract void fit();
	
	/**
	 * Converts a text to a vector.
	 * 
	 * @param  text Text to convert.         
	 * @return vector Vector converted from text.
	 */
	public abstract INDArray vectorFromText(String text);
		
	/**
	 * Calculates the similarity between two vectors.
	 * 
	 * @param  vector1 Vector. 
	 * @param  vector2 Vector to compare.         
	 * @return similarity Similarity between two vectors.
	 */
	public double similarity(INDArray vector1, INDArray vector2) {
		if (vector1 != null && vector2 != null)
			return Transforms.cosineSim(vector1, vector2);
		return -1;
	}
	
	/**
	 * Calculates the similarity between a text and a label.
	 * 
	 * @param  label Label to compare. 
	 * @param  text Text to compare.         
	 * @return similarity Similarity between text and label.
	 */
	public double similarity(String text, String label) {
		INDArray vector1 = createVectorFromText(text);
		INDArray vector2 = documentsLookupTable.get(label);
		return similarity(vector1, vector2);
	}
	
	/**
	 * Calculates the similarities between a text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return similarities List of labels and their similarities.
	 */
	public List<Pair<String, Double>> rankedDocumentsWithSimilaritiesForText(String text) {
		INDArray vector	= createVectorFromText(text);
		
		if(vector != null) {
			List<Pair<String, Double>> similarDocs 	= new ArrayList<Pair<String, Double>>();
			
			// Add
			for(Entry<String, INDArray> entry : documentsLookupTable.entrySet()) {	
				Double similarity = similarity(entry.getValue(), vector);
				similarDocs.add(new Pair<String, Double>(entry.getKey(), similarity));
			}
			
			// Sort 
			Collections.sort(similarDocs, new Comparator<Pair<String, Double>>() {
				public int compare(Pair<String, Double> o1, Pair<String, Double> o2) {
					return o2.getValue().compareTo(o1.getValue());
				}
			});
			
			return similarDocs;
		}
		
		return null;
	}
	
	/**
	 * Calculates the similarities between a text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return similarities List of labels and their similarities.
	 */
	public List<String> rankedDocumentsForText(String text) {

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
	
	/**
	 * Returns the content for the given document label.
	 * 
	 * @param label Label of the document which content was requested.
	 * @return content Content of the document 
	 */
	public String getContentForDocument(String label) {
		return documentsContentLookupTable.get(label);
	}
		
	/**
	 * Updates Documents lookup table.
	 */
	protected void updateDocumentsLookupTable() {
		Map<String, INDArray> lookupTable = new HashMap<String, INDArray>();
		
		// Reset
		documentsLabelAwareIterator.reset();

		// Iterate
		while(documentsLabelAwareIterator.hasNext()) {
			LabelledDocument 	labelledDocument 		= documentsLabelAwareIterator.nextDocument();
			String 				labelledDocumentId 		= labelledDocument.getLabels().get(0);

			INDArray labelledDocumentVector = createVectorFromText(labelledDocument.getContent());
			if (labelledDocumentId != null && labelledDocumentVector != null) {
				lookupTable.put(labelledDocumentId, labelledDocumentVector);
				documentsContentLookupTable.put(labelledDocumentId, labelledDocument.getContent());
			}
		}
		
		// Set
		documentsLookupTable = lookupTable;
	}
	
	@Override
	public Map<String, String> getDocumentContents() {
		return documentsContentLookupTable;
	}
	
	// Private Methods
	
	private static String createStringFromList(List<String> tokens) {
		tokens.removeAll(Arrays.asList(""));		
		return String.join(" ", tokens);
	}
	
	private static boolean validTokens(VocabCache<VocabWord> vocab, List<String> tokens) {
        for (String token : tokens) {
            if (vocab.containsWord(token)) {
                return true;
            }
        }
        return false;
	}
	
	private INDArray createVectorFromText(String text) {
		List<String> tokens = tokenizerFactory.create(text).getTokens();
		if (ModelImpl.validTokens(vocab, tokens)) {
			return vectorFromText(ModelImpl.createStringFromList(tokens));
		}
		return null;
	}
	
}
