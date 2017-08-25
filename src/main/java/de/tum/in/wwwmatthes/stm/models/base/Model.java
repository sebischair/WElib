package de.tum.in.wwwmatthes.stm.models.base;

import java.util.List;

import org.nd4j.linalg.primitives.Pair;

public interface Model {

	/**
	 * Trains the model.
	 */
	public void fit();
	
	/**
	 * Calculates the similarity between a text and a label.
	 * 
	 * @param  label Label to compare. 
	 * @param  text Text to compare.         
	 * @return similarity Similarity between text and label.
	 */
	public double similarity(String text, String label);
	
	/**
	 * Returns the documents ranked by the similarities between a given text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return similarities List of ranked documents.
	 */
	public List<String> rankedDocumentsForText(String text);
	
	/**
	 * Returns the documents ranked by the similarities between a given text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return documents List of ranked documents with their their similarities.
	 */
	public List<Pair<String, Double>> rankedDocumentsWithSimilaritiesForText(String text);
	
}
