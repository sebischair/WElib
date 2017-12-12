package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nd4j.linalg.primitives.Pair;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.Model;

public class EvaluationDataSetItem {
	
	private DataSetItem dataSetItem;
	
	private Double mrr;
	private Double averageInputDocumentRatio;
	private Double averageRank;
	
	private List<EvaluationDataSetItemSimilarity> similarityList 			= new ArrayList<EvaluationDataSetItemSimilarity>();	
	private Map<String, EvaluationDataSetItemOutput> outputMap 			= new HashMap<String, EvaluationDataSetItemOutput>();
	
	void evaluate(Model model) {
		_evaluate(model);
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
	public DataSetItem getDataSetItem() {
		return dataSetItem;
	}
	public void setDataSetItem(DataSetItem dataSetItem) {
		this.dataSetItem = dataSetItem;
	}

	public Map<String, EvaluationDataSetItemOutput> getOutputMap() {
		return outputMap;
	}

	public void setOutputMap(Map<String, EvaluationDataSetItemOutput> outputMap) {
		this.outputMap = outputMap;
	}
	
	public List<EvaluationDataSetItemSimilarity> getSimilarityList() {
		return similarityList;
	}

	public void setSimilarityList(List<EvaluationDataSetItemSimilarity> similarityList) {
		this.similarityList = similarityList;
	}

	public Double getAverageRank() {
		return averageRank;
	}

	public void setAverageRank(Double averageRank) {
		this.averageRank = averageRank;
	}

	// Private Methods
	private void _evaluate(Model model) {
		
		// Ranked Documents
		List<Pair<String, Double>> rankedDocuments = null;
		try {
			rankedDocuments = model.rankedDocumentsWithSimilaritiesForText(dataSetItem.getInput());
		} catch (VocabularyMatchException e) {
			e.printStackTrace();
		}
		
		// Evaluate
		if (rankedDocuments != null) {
			Integer inputLength = dataSetItem.getInput().length();
			
			// Rank & MRR
			Map<String, Integer> rankMap = EvaluationMethod.ranks(dataSetItem.getOutput(), EvaluationDataSetItem.listFromRankedDocuments(rankedDocuments));
			this.mrr = EvaluationMethod.mrrFromMap(rankMap);
			
			// Similarity & Average Document Length
			Double averageDocumentInputRatio = 0.0;
			List<EvaluationDataSetItemSimilarity> similarities = new ArrayList<EvaluationDataSetItemSimilarity>();
			for(Pair<String, Double> pair : rankedDocuments) {
				Integer documentLength = model.getDocumentContents().get(pair.getKey()).length();
				Double documentInputRatio = (double) documentLength / (double) inputLength;
				
				EvaluationDataSetItemSimilarity similarity = new EvaluationDataSetItemSimilarity(pair);
				similarity.setDocumentLength(documentLength);
				similarity.setDocumentInputRatio(documentInputRatio);
				
				averageDocumentInputRatio += documentInputRatio;
			}
			this.similarityList 				= similarities;
			this.averageInputDocumentRatio 	= averageDocumentInputRatio / rankedDocuments.size();
		
			// Output Map
			Double averageRank = 0.0;
			Map<String, EvaluationDataSetItemOutput> outputMap = new HashMap<String, EvaluationDataSetItemOutput>();
			for(String outputDocument : dataSetItem.getOutput()) {
				Integer outputLength = model.getDocumentContents().get(outputDocument).length();
				
				EvaluationDataSetItemOutput output = new EvaluationDataSetItemOutput();
				output.setRank(rankMap.get(outputDocument));
				output.setOutputInputRatio((double) outputLength / (double) inputLength);
				outputMap.put(outputDocument, output);
				
				averageRank += rankMap.get(outputDocument);
			}
			this.outputMap 		= outputMap;
			this.averageRank		= averageRank / dataSetItem.getOutput().size();
			
		} else {
			// Ranking not possible
			this.mrr 						= null;
			this.outputMap 					= null;
			this.similarityList 				= null;
			this.averageInputDocumentRatio 	= null;
			this.averageRank					= null;
		}
	}
	
	// Private Static Methods
		
	private static List<String> listFromRankedDocuments(List<Pair<String, Double>> list) {
		List<String> result = new ArrayList<String>();
		for(Pair<String, Double> pair : list) {	
			result.add(pair.getKey());
		}		
		return result;
	}
	
}
