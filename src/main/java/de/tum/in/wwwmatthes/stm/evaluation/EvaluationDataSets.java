package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.models.Model;

public class EvaluationDataSets {
	
	private DataSets dataSets;
	private Map<String, String> contents; 
	private List<EvaluationDataSet> dataSetList;
	
	// Evaluation
	private Double mrr;
	private Double averageInputDocumentRatio;
	private Double averageRank;
	
	// Public Methods
	
	public void evaluate(Model model) {
		
		// Contents
		this.contents = model.getDocumentContents();
		
		// Evaluate Data Set Items
		for(EvaluationDataSet item : dataSetList) {
			item.evaluate(model);
		}
		
		// Calculate MRR from evaluated Data Set Items
		Double mrr = 0.0;
		Double averageInputDocumentRatio = 0.0;
		Double averageRank = 0.0;
		
		List<EvaluationDataSet> evaluatedItems = getEvaluatedItems();
		if (evaluatedItems.size() > 0) {
			for(EvaluationDataSet item : evaluatedItems) {
				mrr += item.getMrr();
				averageInputDocumentRatio += item.getAverageInputDocumentRatio();
				averageRank += item.getAverageRank();
			}
			this.mrr = mrr / evaluatedItems.size();
			this.averageInputDocumentRatio = averageInputDocumentRatio / evaluatedItems.size();
			this.averageRank = averageRank / evaluatedItems.size();
			
		} else {
			this.mrr = null;
			this.averageInputDocumentRatio = null;
			this.averageRank = null;
		}
	}
	
	// Getters & Setters
	
	public Double getMrr() {
		return mrr;
	}
	public Double getAverageInputDocumentRatio() {
		return averageInputDocumentRatio;
	}
	public DataSets getDataSets() {
		return dataSets;
	}
	public List<EvaluationDataSet> getDataSetList() {
		return dataSetList;
	}
	void setMrr(Double mrr) {
		this.mrr = mrr;
	}
	void setAverageInputDocumentRatio(Double averageInputDocumentRatio) {
		this.averageInputDocumentRatio = averageInputDocumentRatio;
	}
	void setDataSets(DataSets dataSets) {
		this.dataSets = dataSets;
	}
	void setDataSetList(List<EvaluationDataSet> dataSetList) {
		this.dataSetList = dataSetList;
	}
	
	// Private Methods
	
	public Map<String, String> getContents() {
		return contents;
	}

	public void setContents(Map<String, String> contents) {
		this.contents = contents;
	}

	public Double getAverageRank() {
		return averageRank;
	}

	public void setAverageRank(Double averageRank) {
		this.averageRank = averageRank;
	}

	private List<EvaluationDataSet> getEvaluatedItems() {
		if (dataSetList != null) {
			List<EvaluationDataSet> evaluatedItems = new ArrayList<EvaluationDataSet>();
			for(EvaluationDataSet item : dataSetList) {
				if (item.getMrr() != null) {
					evaluatedItems.add(item);
				}
			}
			return evaluatedItems;
		}
		return null;
	}

}
