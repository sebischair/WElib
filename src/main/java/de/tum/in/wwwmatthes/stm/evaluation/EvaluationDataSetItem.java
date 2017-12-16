package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nd4j.linalg.primitives.Pair;

import de.tum.in.wwwmatthes.stm.evaluation.datasets.DataSetItem;
import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.Model;

public class EvaluationDataSetItem extends EvaluationBaseObject {
	
	private DataSetItem dataSetItem;
	
	private Double 	mrr;
	private Integer inputLength;
	
	private Double 	averageDocumentInputLengthRatio;
	private Double 	averageOutputLength;
	private Double 	averageOutputInputLengthRatio;
	
	private Integer 	evaluationMinRank;
	private Integer 	evaluationMaxRank;
	private Double 	evaluationOptimalRank;
	private Double 	evaluationOptimalRankInPercentage;
	
	private Integer 	minMaxRankDifference;
	private Integer 	minRank = Integer.MAX_VALUE;
	private Double 	minRankInPercentage;
	private Integer 	maxRank = Integer.MIN_VALUE;
	private Double 	maxRankInPercentage;
	
	private Double 	averageRank;
	private Double 	averageRankInPercentage;
	private Double 	relativeAverageRankInPercentage;
	
	// Similarities
	private List<EvaluationDataSetItemSimilarity> similarityList;
	
	// Output 
	private Map<String, EvaluationDataSetItemOutput> outputMap;
	
	// Getters & Setters
		
	public Double getMrr() {
		return mrr;
	}

	public Integer getInputLength() {
		return inputLength;
	}

	public Double getAverageDocumentInputLengthRatio() {
		return averageDocumentInputLengthRatio;
	}

	public DataSetItem getDataSetItem() {
		return dataSetItem;
	}

	public Map<String, EvaluationDataSetItemOutput> getOutputMap() {
		return outputMap;
	}
	
	public List<EvaluationDataSetItemSimilarity> getSimilarityList() {
		return similarityList;
	}

	public Double getAverageRank() {
		return averageRank;
	}

	public Double getAverageRankInPercentage() {
		return averageRankInPercentage;
	}

	// Public Static Methods

	public Double getEvaluationOptimalRankInPercentage() {
		return evaluationOptimalRankInPercentage;
	}

	public Double getRelativeAverageRankInPercentage() {
		return relativeAverageRankInPercentage;
	}

	public Integer getEvaluationMinRank() {
		return evaluationMinRank;
	}

	public Double getEvaluationOptimalRank() {
		return evaluationOptimalRank;
	}

	public Integer getEvaluationMaxRank() {
		return evaluationMaxRank;
	}

	public Integer getMinMaxRankDifference() {
		return minMaxRankDifference;
	}

	public Integer getMinRank() {
		return minRank;
	}

	public Double getMinRankInPercentage() {
		return minRankInPercentage;
	}

	public Integer getMaxRank() {
		return maxRank;
	}

	public Double getMaxRankInPercentage() {
		return maxRankInPercentage;
	}

	public Double getAverageOutputLength() {
		return averageOutputLength;
	}

	public void setAverageOutputLength(Double averageOutputLength) {
		this.averageOutputLength = averageOutputLength;
	}

	public Double getAverageOutputInputLengthRatio() {
		return averageOutputInputLengthRatio;
	}

	public void setAverageOutputInputLengthRatio(Double averageOutputInputLengthRatio) {
		this.averageOutputInputLengthRatio = averageOutputInputLengthRatio;
	}

	public static EvaluationDataSetItem evaluate(DataSetItem dataSetItem, Model model) {
		
		// Rank Documents
		List<Pair<String, Double>> rankedDocuments = null;
		try {
			rankedDocuments = model.rankedDocumentsWithSimilaritiesForText(dataSetItem.getInput());
		} catch (VocabularyMatchException e) {
			//e.printStackTrace();
			
			EvaluationDataSetItem evaluationDataSetItem 	= new EvaluationDataSetItem();
			evaluationDataSetItem.dataSetItem			= dataSetItem;
			evaluationDataSetItem.hasErrorAppeared		= true;
			evaluationDataSetItem.errorMessage 			= "Input '" + dataSetItem.getInput() + "' is invalid."; 
			return evaluationDataSetItem;
		}
		
		// Evaluate		
		Integer inputLength 				= dataSetItem.getInput().length();
		Map<String, Integer> rankMap 	= EvaluationMethod.ranks(dataSetItem.getOutput(), EvaluationDataSetItem.listFromRankedDocuments(rankedDocuments));
		
		EvaluationDataSetItem evaluationDataSetItem 	= new EvaluationDataSetItem();
		evaluationDataSetItem.dataSetItem			= dataSetItem;
		evaluationDataSetItem.inputLength 			= inputLength;
		evaluationDataSetItem.mrr					= EvaluationMethod.mrrFromMap(rankMap);
		
		// Similarity & Average Document Length
		Double averageDocumentInputRatio = 0.0;
		List<EvaluationDataSetItemSimilarity> similarities = new ArrayList<EvaluationDataSetItemSimilarity>();
		
		for(Pair<String, Double> pair : rankedDocuments) {
			Integer documentLength 			= model.getDocumentContents().get(pair.getKey()).length();
			Double documentInputLengthRatio 	= (double) documentLength / (double) inputLength;
			
			EvaluationDataSetItemSimilarity similarity = new EvaluationDataSetItemSimilarity(pair);
			similarity.setDocumentLength(documentLength);
			similarity.setDocumentInputLengthRatio(documentInputLengthRatio);
			
			averageDocumentInputRatio += documentInputLengthRatio;
			similarities.add(similarity);
		}
		Integer minRank = 1;
		Integer maxRank = similarities.size();
		
		evaluationDataSetItem.similarityList 					= similarities;
		evaluationDataSetItem.evaluationMinRank					= minRank;
		evaluationDataSetItem.evaluationMaxRank					= maxRank;
		evaluationDataSetItem.evaluationOptimalRank				= maxAverageRankWithOutputCount(dataSetItem.getOutput().size());
		evaluationDataSetItem.evaluationOptimalRankInPercentage	= rankInPercentage(evaluationDataSetItem.evaluationOptimalRank, minRank, maxRank);
		evaluationDataSetItem.averageDocumentInputLengthRatio		= averageDocumentInputRatio / rankedDocuments.size();
	
		// Output Map
		Double averageRank = 0.0;
		Double averageOutputLength = 0.0;
		Double averageOutputInputLengthRatio = 0.0;
		Map<String, EvaluationDataSetItemOutput> outputMap = new HashMap<String, EvaluationDataSetItemOutput>();
		
		for(String outputDocument : dataSetItem.getOutput()) {
			String outputString				= model.getDocumentContents().get(outputDocument);			
			Integer outputLength 			= outputString.trim().length();
			Integer rank						= rankMap.get(outputDocument);
			Double outputInputLengthRatio	= (double) outputLength / (double) inputLength;
						
			EvaluationDataSetItemOutput output = new EvaluationDataSetItemOutput();
			output.setRank(rank);
			output.setRankInPercentage(rankInPercentage(rank, minRank, maxRank));
			output.setOutputLength((long) outputLength);
			output.setOutputInputLengthRatio(outputInputLengthRatio);
			
			outputMap.put(outputDocument, output);
			averageRank 						+= rank;
			averageOutputLength 				+= outputLength;
			averageOutputInputLengthRatio 	+= outputInputLengthRatio;
			
			// Min Max
			if(rank < evaluationDataSetItem.minRank) { evaluationDataSetItem.minRank = rank; }
			if(rank > evaluationDataSetItem.maxRank) { evaluationDataSetItem.maxRank = rank; }
		}

		evaluationDataSetItem.outputMap 							= outputMap;
		
		evaluationDataSetItem.minMaxRankDifference 				= evaluationDataSetItem.maxRank - evaluationDataSetItem.minRank;
		evaluationDataSetItem.minRankInPercentage				= rankInPercentage(evaluationDataSetItem.minRank, minRank, maxRank);
		evaluationDataSetItem.maxRankInPercentage				= rankInPercentage(evaluationDataSetItem.maxRank, minRank, maxRank);
		
		evaluationDataSetItem.averageRank						= averageRank / dataSetItem.getOutput().size();
		evaluationDataSetItem.averageRankInPercentage			= rankInPercentage(evaluationDataSetItem.averageRank, minRank, maxRank);
		evaluationDataSetItem.relativeAverageRankInPercentage 	= rankInPercentage(evaluationDataSetItem.averageRank, evaluationDataSetItem.evaluationOptimalRank, maxRank);
		
		evaluationDataSetItem.averageOutputLength				= averageOutputLength / dataSetItem.getOutput().size();
		evaluationDataSetItem.averageOutputInputLengthRatio		= averageOutputInputLengthRatio / dataSetItem.getOutput().size();
				
		return evaluationDataSetItem;
	}
	
	// Public Static Methods
	
	public static double maxAverageRankWithOutputCount(int outputCount) {
		return sum_from_1_to_x(outputCount) / (double) outputCount;
	}
	
	// Private Static Methods
		
	private static List<String> listFromRankedDocuments(List<Pair<String, Double>> list) {
		List<String> result = new ArrayList<String>();
		for(Pair<String, Double> pair : list) {	
			result.add(pair.getKey());
		}		
		return result;
	}
	
	private static int sum_from_1_to_x(int x) {
		int result = 0;
		for(int i = 0; i <= x; i++) {
			result += i;
		}
		return result;
	}
	
	static Double rankInPercentage(double rank, double minRank, double maxRank) {
		return 1 - (rank - minRank) / (maxRank - minRank);
	}
	
}
