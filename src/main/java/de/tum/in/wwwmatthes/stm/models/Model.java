package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelledDocument;
import org.nd4j.linalg.primitives.Pair;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;

public interface Model {
	
	/**
	 * Trains the model.
	 */
	public void fit() throws VocabularyMatchException;
	
	/**
	 * Calculates the similarity between a text and a label.
	 * 
	 * @param  label Label to compare. 
	 * @param  text Text to compare.         
	 * @return similarity Similarity between text and label.
	 */
	public double similarity(String text, String label) throws VocabularyMatchException;
	
	/**
	 * Returns the documents ranked by the similarities between a given text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return similarities List of ranked documents.
	 */
	public List<String> rankedDocumentsForText(String text) throws VocabularyMatchException;
	
	/**
	 * Returns the documents ranked by the similarities between a given text and all labels.
	 * 
	 * @param  text Text to compare.         
	 * @return documents List of ranked documents with their their similarities.
	 */
	public List<Pair<String, Double>> rankedDocumentsWithSimilaritiesForText(String text) throws VocabularyMatchException;
	
	/**
	 * Returns the content for the given document label.
	 * 
	 * @param label Label of the document which content was requested.
	 * @return content Content of the document 
	 */
	public String getContentForDocument(String label);
	
	/**
	 * Returns a dictionary with the label as key and content as value.
	 * 
	 * @return dictionary Dictionary of labels and its contents
	 */
	public Map<String, String> getDocumentContents();
	
	
	public void putDocuments(LabelAwareIterator documentsLabelAwareIterator) throws VocabularyMatchException;
	public void putDocuments(Map<String, String> documents) throws VocabularyMatchException;
		
	/**
	 * Writes the Model.
	 * 
	 */
	public void write(File directory, String identifier) throws IOException;

	/**
	 * Tokenizes a string
	 * 
	 */
	public String processString(String text);
	
		
}
