package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.List;

public class DataSetItem {
	
	private String label;
	private String input;
	private List<String> output;
	
	public DataSetItem(String label, String input, List<String> output) {
		this.label 	= label;
		this.input	= input;
		this.output	= output;
	}

	public String getLabel() {
		return label;
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
			return "DataSetItem [label=" + label + ", input=" + input.substring(0, max) + "... , output=" + output + "]";
		} else {
			return "DataSetItem [label=" + label + ", input=" + input + ", output=" + output + "]";
		}
	}

}
