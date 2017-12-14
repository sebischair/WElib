package de.tum.in.wwwmatthes.stm.evaluation;

public class EvaluationBaseObject {
	
	// Error Handling
	protected boolean 	hasErrorAppeared = false;
	protected String 	errorMessage;

	public boolean hasErrorAppeared() {
		return hasErrorAppeared;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}	
	
}
