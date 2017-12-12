package de.tum.in.wwwmatthes.stm.exceptions;

public class VocabularyMatchException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VocabularyMatchException() {
		super("Text passed for inference has no matches in model vocabulary.");
	}
	
	public VocabularyMatchException(String vocabulary) {
		super("Text passed for inference has no matches in model vocabulary: " + vocabulary);
	}
	
}
