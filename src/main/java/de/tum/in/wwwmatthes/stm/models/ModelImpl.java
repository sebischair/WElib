package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.resource.ResourceInitializationException;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.ops.transforms.Transforms;
import org.nd4j.linalg.primitives.Pair;

import de.tum.in.wwwmatthes.stm.models.config.Config;

abstract class ModelImpl implements Model {
	
	private Map<String, String> documentsContentLookupTable = new HashMap<String, String>(); // Only for debugging
	private Map<String, INDArray> documentsLookupTable = new HashMap<String, INDArray>();
	
	// Variables
	protected LabelAwareIterator 	documentsLabelAwareIterator;
	protected TokenizerFactory 		tokenizerFactory;
	
	// Private Variables
	
	ModelImpl(Config config) {
		super();
		
		// Documents Label Aware Iterator
		this.documentsLabelAwareIterator = new FileLabelAwareIterator.Builder()
	              .addSourceFolder(config.getDocumentsSourceFile())
	              .build();
		
		// Tokenizer Factory
		this.tokenizerFactory = new UimaTokenizerFactory(UimaTokenizerFactory.defaultAnalysisEngine());
		this.tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor() {
			@Override
			public String preProcess(String token) {
				return token;
			}
		});
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
		return Transforms.cosineSim(vector1, vector2);
	}
	
	/**
	 * Calculates the similarity between a text and a label.
	 * 
	 * @param  label Label to compare. 
	 * @param  text Text to compare.         
	 * @return similarity Similarity between text and label.
	 */
	public double similarity(String text, String label) {
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
	public List<Pair<String, Double>> rankedDocumentsWithSimilaritiesForText(String text) {
		INDArray vector 							= vectorFromText(text);
		List<Pair<String, Double>> similarDocs 	= new ArrayList<Pair<String, Double>>();
		
		// Add
		for(Entry<String, INDArray> entry : documentsLookupTable.entrySet()) {	
			double similarity = similarity(entry.getValue(), vector);
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
	
	/**
	 * Calculates the similarities between a text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return similarities List of labels and their similarities.
	 */
	public List<String> rankedDocumentsForText(String text) {

		List<Pair<String, Double>> similarDocs = rankedDocumentsWithSimilaritiesForText(text);
		
		List<String> list = new ArrayList<String>();
		for(Pair<String, Double> pair : similarDocs) {	
			list.add(pair.getKey());
		}		
		return list;
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
			INDArray 			labelledDocumentVector 	= vectorFromText(labelledDocument.getContent());
			
			if (labelledDocumentId != null && labelledDocumentVector != null) {
				lookupTable.put(labelledDocumentId, labelledDocumentVector);
				documentsContentLookupTable.put(labelledDocumentId, labelledDocument.getContent());
			}
		}
		
		// Set
		documentsLookupTable = lookupTable;
	}
	
}
