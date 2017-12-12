package de.tum.in.wwwmatthes.stm.evaluation.datasets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.tum.in.wwwmatthes.stm.models.Model;

public class DataSet {
	
	private String 								identifier;
	private List<DataSetItem> 					items;
	private transient Map<String, DataSetItem> 	map;
		
	public DataSet(String identifier, List<DataSetItem> items) {
		this.identifier = identifier;
		this.items 		= items;
		this.map 		= new HashMap<String, DataSetItem>(); 
		
		for(DataSetItem item : items) {
			this.map.put(item.getIdentifier(), item);
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
	
	public String getIdentifier() {
		return identifier;
	}
	
	public List<DataSetItem> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return "DataSet [identifier=" + identifier + "]";
	}
	
}
