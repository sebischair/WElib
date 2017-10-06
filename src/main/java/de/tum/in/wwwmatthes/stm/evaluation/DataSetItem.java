package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.List;

import de.tum.in.wwwmatthes.stm.models.Model;

public class DataSetItem {
	
	private String identifier;
	private String input;
	private List<String> output;
	
	private Double MRR = -1.0;
	
	public DataSetItem(String identifier, String input, List<String> output) {
		this.identifier 	= identifier;
		this.input		= input;
		this.output		= output;
		this.MRR 		= null;
	}
	
	public void evaluateWithModel(Model model) {
		try {
			List<String> rankedDocuments = model.rankedDocumentsForText(getInput());
			this.MRR = Evaluation.mrr(getOutput(), rankedDocuments);
		} catch (org.nd4j.linalg.exception.ND4JIllegalStateException e) {
			this.MRR = null;
		}	
	}
	
	public void resetEvaluation() {
		this.MRR = null;
	}
	
	/*
	 * Getters & Setters
	 */

	public String getIdentifier() {
		return identifier;
	}

	public String getInput() {
		return input;
	}

	public List<String> getOutput() {
		return output;
	}
	
	public Double getMRR() {
		return MRR;
	}

	@Override
	public String toString() {
		int max = Math.min(input.length(), 20);
		if (input.length() > max) {
			return "DataSetItem [identifier=" + identifier + ", input=" + input.substring(0, max) + "... , output=" + output + "]";
		} else {
			return "DataSetItem [identifier=" + identifier + ", input=" + input + ", output=" + output + "]";
		}
	}

}
