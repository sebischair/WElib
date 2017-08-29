package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.List;

public class DataSetItem {
	
	private String key;
	private String input;
	private List<String> output;
	
	public DataSetItem(String key, String input, List<String> output) {
		this.key 	= key;
		this.input	= input;
		this.output	= output;
	}

	public String getKey() {
		return key;
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
			return "DataSetItem [key=" + key + ", input=" + input.substring(0, max) + "... , output=" + output + "]";
		} else {
			return "DataSetItem [key=" + key + ", input=" + input + ", output=" + output + "]";
		}
	}

}
