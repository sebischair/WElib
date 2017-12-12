package de.tum.in.wwwmatthes.stm.evaluation.datasets;

import java.util.List;

public class DataSetItem {
	
	private String identifier;
	private String input;
	private List<String> output;
	
	public DataSetItem(String identifier, String input, List<String> output) {
		this.identifier 		= identifier;
		this.input			= input;
		this.output			= output;
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
