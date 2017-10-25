package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.tum.in.wwwmatthes.stm.models.Model;

public class DataSet {
	
	private String 								identifier;
	private List<DataSetItem> 					items;
	private transient Map<String, DataSetItem> 	map;
	
	private Double MRR;
	
	public DataSet(String identifier, List<DataSetItem> items) {
		this.identifier = identifier;
		this.items 		= items;
		this.map 		= new HashMap<String, DataSetItem>(); 
		
		for(DataSetItem item : items) {
			this.map.put(item.getIdentifier(), item);
		}
	}
	
	public void evaluateWithModel(Model model) {
		
		// Evaluate
		for(DataSetItem item : getItems()) {
			item.evaluateWithModel(model);
		}
		
		// Calculate MRR from evaluated Data Set Items
		double mrr = 0;
		List<DataSetItem> evaluatedItems = getEvaluatedItems();
		
		if (evaluatedItems.size() > 0) {
			for(DataSetItem item : evaluatedItems) {
				item.evaluateWithModel(model);
				mrr += item.getMRR();
			}
			System.out.println(mrr + " " + evaluatedItems.size());
			this.MRR = mrr / evaluatedItems.size();
		} else {
			this.MRR = null;
		}
	}
	
	public void resetEvaluation() {
		for(DataSetItem item : getItems()) {
			item.resetEvaluation();
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
	
	private List<DataSetItem> getEvaluatedItems() {
		List<DataSetItem> evaluatedItems = new ArrayList<DataSetItem>();
		for(DataSetItem item : getItems()) {
			if (item.getMRR() != null && item.getMRR() != null) {
				evaluatedItems.add(item);
			}
		}
		return evaluatedItems;
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
	
	public Double getMRR() {
		return MRR;
	}

	@Override
	public String toString() {
		return "DataSet [identifier=" + identifier + ", MRR=" + MRR + "]";
	}
	
}
