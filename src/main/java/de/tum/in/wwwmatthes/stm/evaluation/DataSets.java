package de.tum.in.wwwmatthes.stm.evaluation;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import de.tum.in.wwwmatthes.stm.models.Model;

public class DataSets {

	private List<DataSet> 					items;
	private transient Map<String, DataSet> 	map;
	
	private Double MRR;
	
	public DataSets(List<DataSet> items) {
		this.items 		= items;
		this.map 		= new HashMap<String, DataSet>(); 
		
		for(DataSet item : items) {
			this.map.put(item.getIdentifier(), item);
		}
	}
	
	public DataSets(DataSet item) {
		this(Arrays.asList(item));
	}
	
	public void evaluateWithModel(Model model) {
		double mrr = 0;
		for(DataSet item : getItems()) {
			item.evaluateWithModel(model);
			mrr += item.getMRR();
		}
		this.MRR = mrr / getItems().size();
	}
	
	public void writeToFile(File file) throws IOException {
		String json = new Gson().toJson(this);
		FileUtils.writeStringToFile(file, json);
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
	
	/*
	 * Getters & Setters
	 */
	
	public List<DataSet> getItems() {
		return items;
	}
	
	public Double getMRR() {
		return MRR;
	}

	@Override
	public String toString() {
		String output = "DataSets:\n";
		for(DataSet item : items) {
			output += item + "\n";
		}
		return output;
	}
	
}
