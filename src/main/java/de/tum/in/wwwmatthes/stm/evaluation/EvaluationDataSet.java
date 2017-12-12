package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.List;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;
import de.tum.in.wwwmatthes.stm.models.Model;

public class EvaluationDataSet {

	private Double mrr;
	private Double averageInputDocumentRatio;
	private Double averageRank;
	private DataSet dataSet;
	
	private List<EvaluationDataSetItem> dataSetItemList;
		
	// Public Methods
	
	public void evaluate(Model model) {
		
		// Evaluate Data Set Items
		for(EvaluationDataSetItem item : dataSetItemList) {
			item.evaluate(model);
		}
		
		// Calculate MRR from evaluated Data Set Items
		Double mrr 						= 0.0;
		Double averageInputDocumentRatio = 0.0;
		Double averageRank 				= 0.0;
		
		List<EvaluationDataSetItem> evaluatedItems = getEvaluatedItems();
		if (evaluatedItems.size() > 0) {
			for(EvaluationDataSetItem item : evaluatedItems) {
				mrr += item.getMrr();
				averageInputDocumentRatio += item.getAverageInputDocumentRatio();
				averageRank += item.getAverageRank();
			}
			this.mrr 						= mrr / evaluatedItems.size();
			this.averageInputDocumentRatio 	= averageInputDocumentRatio / evaluatedItems.size();
			this.averageRank 				= averageRank / evaluatedItems.size();
			
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
	public void setMrr(Double mrr) {
		this.mrr = mrr;
	}
	public Double getAverageInputDocumentRatio() {
		return averageInputDocumentRatio;
	}
	public void setAverageInputDocumentRatio(Double averageInputDocumentRatio) {
		this.averageInputDocumentRatio = averageInputDocumentRatio;
	}
	public DataSet getDataSet() {
		return dataSet;
	}
	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}
	public List<EvaluationDataSetItem> getDataSetItemList() {
		return dataSetItemList;
	}
	public void setDataSetItemList(List<EvaluationDataSetItem> dataSetItemList) {
		this.dataSetItemList = dataSetItemList;
	}
	
	// Private Methods
	
	public Double getAverageRank() {
		return averageRank;
	}

	public void setAverageRank(Double averageRank) {
		this.averageRank = averageRank;
	}

	private List<EvaluationDataSetItem> getEvaluatedItems() {
		List<EvaluationDataSetItem> evaluatedItems = new ArrayList<EvaluationDataSetItem>();
		for(EvaluationDataSetItem item : dataSetItemList) {
			if (item.getMrr() != null) {
				evaluatedItems.add(item);
			}
		}
		return evaluatedItems;
	}
	
}
