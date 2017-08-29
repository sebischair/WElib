package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSet {
	
	private List<DataSetItem> 		items;
	private Map<String, DataSetItem> map;
	
	public DataSet(List<DataSetItem> items) {
		this.items 	= items;
		this.map 	= new HashMap<String, DataSetItem>(); 
		
		for(DataSetItem item : items) {
			this.map.put(item.getKey(), item);
		}
	}
	
	/**
	 * Return dataset for a specific label.
	 * 
	 * @param label Identifier for specific dataset
	 * @return dataset Dataset
	 */
	public DataSetItem dataSetItemForKey(String label) {
		return map.get(label);
	}
	
	/*
	 * Getters & Setters
	 */
	
	public List<DataSetItem> getItems() {
		return items;
	}

	@Override
	public String toString() {
		String output = "DataSet:\n";
		for(DataSetItem item : items) {
			output += item + "\n";
		}
		return output;
	}
	
}
