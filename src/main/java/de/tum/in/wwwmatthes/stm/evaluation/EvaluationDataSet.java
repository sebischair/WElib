package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.List;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;

public class EvaluationDataSet extends EvaluationDataSetItemCollection {
	
	private DataSet	dataSet;
	private List<EvaluationDataSetItem> evaluatedDataSetItemList;
	private List<EvaluationDataSetItem> evaluatedDataSetItemListWithError;
	
	public DataSet getDataSet() {
		return dataSet;
	}

	public List<EvaluationDataSetItem> getEvaluatedDataSetItemList() {
		return evaluatedDataSetItemList;
	}

	void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}

	void setEvaluatedDataSetItemList(List<EvaluationDataSetItem> evaluatedDataSetItemList) {
		this.evaluatedDataSetItemList = evaluatedDataSetItemList;
	}

	public List<EvaluationDataSetItem> getEvaluatedDataSetItemListWithError() {
		return evaluatedDataSetItemListWithError;
	}

	void setEvaluatedDataSetItemListWithError(List<EvaluationDataSetItem> evaluatedDataSetItemListWithError) {
		this.evaluatedDataSetItemListWithError = evaluatedDataSetItemListWithError;
	}
	
}
