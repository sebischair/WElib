package de.tum.in.wwwmatthes.stm.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.tum.in.wwwmatthes.stm.models.Model;

public class DataSets {
	
	private String 							identifier;
	private List<DataSet> 					items;
	private Map<String, String>            	contents;
	
	private transient Map<String, DataSet> 	map;
	
	private Double MRR;
	
	public DataSets() {
		
	}
	
	public DataSets(List<DataSet> items) {
		this.items 		= items;
		this.map 		= new HashMap<String, DataSet>(); 
		this.contents 	= new HashMap<String, String>(); 
		
		for(DataSet item : items) {
			this.map.put(item.getIdentifier(), item);
		}
	}
	
	public DataSets(DataSet item) {
		this(Arrays.asList(item));
	}
	
	public void evaluateWithModel(Model model) {
		
		// Evaluate
		for(DataSet item : getItems()) {
			item.evaluateWithModel(model);
		}
		
		// Save Document Contents
		this.contents = model.getDocumentContents();
		
		// Calculate MRR from evaluated Data Set Items
		double mrr = 0;
		List<DataSet> evaluatedItems = getEvaluatedItems();
		
		if (evaluatedItems.size() > 0) {
			for(DataSet item : evaluatedItems) {
				item.evaluateWithModel(model);
				mrr += item.getMRR();
			}
			this.MRR = mrr / evaluatedItems.size();
		} else {
			this.MRR = null;
		}
		
		this.MRR = mrr / getItems().size();
	}
	
	public void resetEvaluation() {
		for(DataSet item : getItems()) {
			item.resetEvaluation();
		}
	}
	
	public void writeToFile(File file) throws IOException {
		FileUtils.writeStringToFile(file, toJson());
	}
	
	public static DataSets readFromFile(File file) throws IOException {
		String content = FileUtils.readFileToString(file);
		return DataSets.fromJson(content);
	}
	
	/**
	 * Return dataset for a specific label.
	 * 
	 * @param label Identifier for specific dataset
	 * @return dataset Dataset
	 */
	public DataSet dataSetItemForIdentifier(String identifier) {
		return map.get(identifier);
	}
	
	private List<DataSet> getEvaluatedItems() {
		List<DataSet> evaluatedItems = new ArrayList<DataSet>();
		for(DataSet item : getItems()) {
			if (item.getMRR() != null && item.getMRR() != null) {
				evaluatedItems.add(item);
			}
		}
		return evaluatedItems;
	}
	
	/*
	 * Getters & Setters
	 */
	
	public List<DataSet> getItems() {
		return items;
	}
	
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Double getMRR() {
		return MRR;
	}

	public Map<String, String> getContents() {
		return contents;
	}

	@Override
	public String toString() {
		String output = "DataSets:\n";
		for(DataSet item : items) {
			output += item + "\n";
		}
		return output;
	}
	
	// Public Methods - JSON
	
	private String toJson() {
		Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
		return gson.toJson(this);
	}
	
	private static DataSets fromJson(String json) {
		Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
		return gson.fromJson(json, DataSets.class);
	}
	
}
