package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.models.Model;

public class Evaluation {
	
	// Properties
	private DataSets dataSets;
	private EvaluationDataSets evaluationDataSets;
	
	public Evaluation(DataSets dataSets) {
		this.dataSets = dataSets;
	}
	
	public void evaluate(Model model) {
		this.evaluationDataSets = Evaluation.initialize(dataSets);
		this.evaluationDataSets.evaluate(model);
	}
	
	// Getters & Setters
	
	public EvaluationDataSets getEvaluationDataSets() {
		return evaluationDataSets;
	}

	public void setEvaluationDataSets(EvaluationDataSets evaluationDataSets) {
		this.evaluationDataSets = evaluationDataSets;
	}
	
	public Double getMrr() {
		if (evaluationDataSets != null) {
			return evaluationDataSets.getMrr();
		}
		return null;
	}
	
	public Double getAverageRank() {
		if (evaluationDataSets != null) {
			return evaluationDataSets.getAverageRank();
		}
		return null;
	}
	
	// Private Helper Methods
	
	private static EvaluationDataSets initialize(DataSets dataSets) {

		List<EvaluationDataSet> evaluationDataSetList = new ArrayList<EvaluationDataSet>();
		for(DataSet dataSet : dataSets.getItems()) {
			
			List<EvaluationDataSetItem> evaluationDataSetItemList = new ArrayList<EvaluationDataSetItem>();
			for(DataSetItem dataSetItem : dataSet.getItems()) {
								
				EvaluationDataSetItem evaluationDataSetItem = new EvaluationDataSetItem();
				evaluationDataSetItem.setDataSetItem(dataSetItem);
				
				// Add Data Set Item
				evaluationDataSetItemList.add(evaluationDataSetItem);
			}
			
			EvaluationDataSet evaluationDataSet = new EvaluationDataSet();
			evaluationDataSet.setDataSet(dataSet);
			evaluationDataSet.setDataSetItemList(evaluationDataSetItemList);
			
			// Add Data Set
			evaluationDataSetList.add(evaluationDataSet);
		}
		
		EvaluationDataSets evaluationDataSets = new EvaluationDataSets();
		evaluationDataSets.setDataSets(dataSets);
		evaluationDataSets.setDataSetList(evaluationDataSetList);
		return evaluationDataSets;
	}
		
}
