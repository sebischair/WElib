package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSet;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSets;
import de.tum.in.wwwmatthes.stm.models.Model;

public class EvaluationDataSets extends EvaluationDataSetItemCollection {
	
	// Attributes
	private DataSets 			dataSets;
	private Map<String, String> 	contents; 
	
	private List<EvaluationDataSet> evaluatedDataSetList;
	private List<EvaluationDataSet> evaluatedDataSetListWithError;
		
	// Public Methods: Evaluation
	public static EvaluationDataSets evaluate(DataSets dataSets, Model model) {
		
		// Initialize Map & Evaluate Data Set Item
		Map<DataSet, List<EvaluationDataSetItem>> evaluatedDataSetItemMap 			= new HashMap<DataSet, List<EvaluationDataSetItem>>();
		Map<DataSet, List<EvaluationDataSetItem>> evaluatedDataSetItemMapWithError 	= new HashMap<DataSet, List<EvaluationDataSetItem>>();
		for(DataSet dataSet: dataSets.getItems()) {
			evaluatedDataSetItemMap.put(dataSet, new ArrayList<EvaluationDataSetItem>());
			evaluatedDataSetItemMapWithError.put(dataSet, new ArrayList<EvaluationDataSetItem>());
			
			for(DataSetItem dataSetItem: dataSet.getItems()) {
				EvaluationDataSetItem evaluatedDataSetItem = EvaluationDataSetItem.evaluate(dataSetItem, model);
				if(!evaluatedDataSetItem.hasErrorAppeared()) {
					evaluatedDataSetItemMap.get(dataSet).add(evaluatedDataSetItem);
				} else {
					evaluatedDataSetItemMapWithError.get(dataSet).add(evaluatedDataSetItem);
				}
			}
		}
		
		// Data Sets
		List<EvaluationDataSetItem> evaluatedDataSetItemList = new ArrayList<EvaluationDataSetItem>();
		for(DataSet key: evaluatedDataSetItemMap.keySet()) {
			evaluatedDataSetItemList.addAll(evaluatedDataSetItemMap.get(key));
		}
		
		EvaluationDataSets evaluationDataSets = (EvaluationDataSets) EvaluationDataSetItemCollection.evaluate(new EvaluationDataSets(), evaluatedDataSetItemList, model);
		evaluationDataSets.contents = model.getDocumentContents();
		evaluationDataSets.dataSets = dataSets;
		
		// Data Set
		List<EvaluationDataSet> evaluatedDataSetList = new ArrayList<EvaluationDataSet>();
		List<EvaluationDataSet> evaluatedDataSetListWithError = new ArrayList<EvaluationDataSet>();
		for(DataSet dataSet: evaluatedDataSetItemMap.keySet()) {
			List<EvaluationDataSetItem> evaluatedDataSetItemListPerDataSet = evaluatedDataSetItemMap.get(dataSet);
			List<EvaluationDataSetItem> evaluatedDataSetItemListWithErrorPerDataSet = evaluatedDataSetItemMapWithError.get(dataSet);
			EvaluationDataSet evaluationDataSet = (EvaluationDataSet) EvaluationDataSetItemCollection.evaluate(new EvaluationDataSet(), evaluatedDataSetItemListPerDataSet, model);
			evaluationDataSet.setDataSet(dataSet);
			evaluationDataSet.setEvaluatedDataSetItemList(evaluatedDataSetItemListPerDataSet);
			evaluationDataSet.setEvaluatedDataSetItemListWithError(evaluatedDataSetItemListWithErrorPerDataSet);
			
			if (!evaluationDataSet.hasErrorAppeared()) {
				evaluatedDataSetList.add(evaluationDataSet);
			} else {
				evaluatedDataSetListWithError.add(evaluationDataSet);
			}			
		}

		evaluationDataSets.evaluatedDataSetList 			= evaluatedDataSetList;
		evaluationDataSets.evaluatedDataSetListWithError = evaluatedDataSetListWithError;
		return evaluationDataSets;
	}

	public DataSets getDataSets() {
		return dataSets;
	}

	public Map<String, String> getContents() {
		return contents;
	}

	public List<EvaluationDataSet> getEvaluatedDataSetList() {
		return evaluatedDataSetList;
	}

	public List<EvaluationDataSet> getEvaluatedDataSetListWithError() {
		return evaluatedDataSetListWithError;
	}
	
}
