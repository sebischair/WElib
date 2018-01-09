package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.List;

import de.tum.in.wwwmatthes.stm.models.Model;

public class EvaluationDataSetItemCollection extends EvaluationBaseObject {

	private Double 	averageMrr;
	private Double 	averageInputLength;
	private Double 	averageDocumentInputLengthRatio;
	private Double 	averageOutputLength;
	private Double 	averageOutputInputLengthRatio;
	
	private Double 	averageEvaluationMinRank;
	private Double 	averageEvaluationMaxRank;
	private Double 	averageEvaluationOptimalRank;
	private Double 	averageEvaluationOptimalRankInPercentage;
	
	private Integer 	minMaxRankDifference;
	private Integer 	minRank = Integer.MAX_VALUE;
	private Double 	minRankInPercentage;
	private Integer 	maxRank = Integer.MIN_VALUE;
	private Double 	maxRankInPercentage;
	
	private Double 	averageMinMaxRankDifference;
	private Double 	averageMinRank;
	private Double 	averageMinRankInPercentage;
	private Double 	averageMaxRank;
	private Double 	averageMaxRankInPercentage;
	
	private Double 	averageRank;
	private Double 	averageRankInPercentage;
	private Double 	relativeAverageRankInPercentage;
	
	private Double 	averageBestRank;
	private Double 	averageBestRankInPercentage;
	private Double 	relativeAverageBestRankInPercentage;
		
	public static EvaluationDataSetItemCollection evaluate(EvaluationDataSetItemCollection evaluationCollection, List<EvaluationDataSetItem> dataSetItems, Model model) {
		
		// Evaluate Data Set Items
		Integer dataSetItemListSize = dataSetItems.size();
		if(dataSetItemListSize > 0) {
			
			Double averageMrrSum 				= 0.0;
			Double averageInputLengthSum			= 0.0;
			Double averageInputDocumentRatioSum	= 0.0;
			Double averageOutputLength			= 0.0;
			Double averageOutputInputLengthRatio	= 0.0;
			
			Double averageEvaluationMinRankSum 					= 0.0;
			Double averageEvaluationMaxRankSum 					= 0.0;
			Double averageEvaluationOptimalRankSum 				= 0.0;
			Double averageEvaluationOptimalRankInPercentageSum	= 0.0;
			
			Double averageMinMaxRankDifferenceSum 				= 0.0;
			Double averageMinRankSum 							= 0.0;
			Double averageMinRankInPercentageSum 				= 0.0;
			Double averageMaxRank								= 0.0;
			Double averageMaxRankInPercentageSum					= 0.0;
			
			Double averageRankSum 								= 0.0;
			Double averageRankInPercentageSum					= 0.0;
			Double relativeAverageRankInPercentageSum			= 0.0;
			
			Double averageBestRankSum 								= 0.0;
			Double averageBestRankInPercentageSum					= 0.0;
			Double relativeAverageBestRankInPercentageSum			= 0.0;
			
			for(EvaluationDataSetItem item : dataSetItems) {
				averageMrrSum 								+= item.getMrr();
				averageInputLengthSum						+= item.getInputLength();
				averageInputDocumentRatioSum 				+= item.getAverageDocumentInputLengthRatio();
				averageOutputLength 							+= item.getAverageOutputLength();
				averageOutputInputLengthRatio 				+= item.getAverageOutputInputLengthRatio();
				
				averageEvaluationMinRankSum 					+= item.getEvaluationMinRank();
				averageEvaluationMaxRankSum 					+= item.getEvaluationMaxRank();
				averageEvaluationOptimalRankSum 				+= item.getEvaluationOptimalRank();
				averageEvaluationOptimalRankInPercentageSum	+= item.getEvaluationOptimalRankInPercentage();
				
				averageMinMaxRankDifferenceSum 				+= item.getMinMaxRankDifference();
				averageMinRankSum 							+= item.getMinRank();
				averageMinRankInPercentageSum 				+= item.getMinRankInPercentage();
				averageMaxRank								+= item.getMaxRank();
				averageMaxRankInPercentageSum				+= item.getMaxRankInPercentage();
				
				averageRankSum 								+= item.getAverageRank();
				averageRankInPercentageSum					+= item.getAverageRankInPercentage();
				relativeAverageRankInPercentageSum 			+= item.getRelativeAverageRankInPercentage();
				
				averageBestRankSum 								+= item.getBestRank();
				averageBestRankInPercentageSum					+= item.getBestRankInPercentage();
				relativeAverageBestRankInPercentageSum 			+= item.getRelativeBestRankInPercentage();
				
				// Min Max
				Integer minRank = item.getMinRank();
				Integer maxRank = item.getMaxRank();
				if(minRank < evaluationCollection.minRank) { evaluationCollection.minRank = minRank; }
				if(maxRank > evaluationCollection.maxRank) { evaluationCollection.maxRank = maxRank; }
			}
			
			evaluationCollection.averageMrr 									= averageMrrSum 					/ dataSetItemListSize;
			evaluationCollection.averageInputLength 							= averageInputLengthSum			/ dataSetItemListSize;
			evaluationCollection.averageDocumentInputLengthRatio 				= averageInputDocumentRatioSum 	/ dataSetItemListSize;
			evaluationCollection.averageOutputLength 						= averageOutputLength 			/ dataSetItemListSize;
			evaluationCollection.averageOutputInputLengthRatio 				= averageOutputInputLengthRatio	/ dataSetItemListSize;
			
			evaluationCollection.averageEvaluationMinRank					= averageEvaluationMinRankSum 					/ dataSetItemListSize;
			evaluationCollection.averageEvaluationMaxRank					= averageEvaluationMaxRankSum 					/ dataSetItemListSize;
			evaluationCollection.averageEvaluationOptimalRank 				= averageEvaluationOptimalRankSum 				/ dataSetItemListSize;
			evaluationCollection.averageEvaluationOptimalRankInPercentage 	= averageEvaluationOptimalRankInPercentageSum 	/ dataSetItemListSize;
			
			evaluationCollection.averageMinMaxRankDifference 					= averageMinMaxRankDifferenceSum 	/ dataSetItemListSize;
			evaluationCollection.averageMinRank								= averageMinRankSum 					/ dataSetItemListSize;
			evaluationCollection.averageMinRankInPercentage 					= averageMinRankInPercentageSum 		/ dataSetItemListSize;
			evaluationCollection.averageMaxRank								= averageMaxRank 					/ dataSetItemListSize;
			evaluationCollection.averageMaxRankInPercentage 					= averageMaxRankInPercentageSum 		/ dataSetItemListSize;
			
			evaluationCollection.averageRank 								= averageRankSum 					/ dataSetItemListSize;
			evaluationCollection.averageRankInPercentage 					= averageRankInPercentageSum 		/ dataSetItemListSize;
			evaluationCollection.relativeAverageRankInPercentage 				= relativeAverageRankInPercentageSum	/ dataSetItemListSize;
			
			Double minRank = evaluationCollection.averageEvaluationMinRank;
			Double maxRank = evaluationCollection.averageEvaluationMaxRank;
			
			evaluationCollection.averageBestRank								= averageBestRankSum 						/ dataSetItemListSize;
			evaluationCollection.averageBestRankInPercentage					= averageBestRankInPercentageSum 			/ dataSetItemListSize;
			evaluationCollection.relativeAverageBestRankInPercentage 			= relativeAverageBestRankInPercentageSum 	/ dataSetItemListSize;
			
			evaluationCollection.minMaxRankDifference					= evaluationCollection.maxRank - evaluationCollection.minRank;
			evaluationCollection.minRankInPercentage						= EvaluationDataSetItem.rankInPercentage(evaluationCollection.minRank, minRank, maxRank);
			evaluationCollection.maxRankInPercentage						= EvaluationDataSetItem.rankInPercentage(evaluationCollection.maxRank, minRank, maxRank);
			
		} else {
			evaluationCollection.hasErrorAppeared 	= true;
			evaluationCollection.errorMessage 		= "No data set items available.";
		}
		
		return evaluationCollection;
	}

	public Double getAverageMrr() {
		return averageMrr;
	}

	public Double getAverageDocumentInputLengthRatio() {
		return averageDocumentInputLengthRatio;
	}

	public Double getAverageRank() {
		return averageRank;
	}

	public Double getAverageRankInPercentage() {
		return averageRankInPercentage;
	}
	
	public Double getAverageInputLength() {
		return averageInputLength;
	}

	public Double getAverageEvaluationMinRank() {
		return averageEvaluationMinRank;
	}

	public Double getAverageEvaluationMaxRank() {
		return averageEvaluationMaxRank;
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

	public Double getAverageEvaluationOptimalRank() {
		return averageEvaluationOptimalRank;
	}

	public Double getAverageEvaluationOptimalRankInPercentage() {
		return averageEvaluationOptimalRankInPercentage;
	}

	public Double getAverageMinMaxRankDifference() {
		return averageMinMaxRankDifference;
	}

	public Double getAverageMinRank() {
		return averageMinRank;
	}

	public Double getAverageMinRankInPercentage() {
		return averageMinRankInPercentage;
	}

	public Double getAverageMaxRank() {
		return averageMaxRank;
	}

	public Double getAverageMaxRankInPercentage() {
		return averageMaxRankInPercentage;
	}

	public Double getRelativeAverageRankInPercentage() {
		return relativeAverageRankInPercentage;
	}

	public Double getAverageOutputLength() {
		return averageOutputLength;
	}

	public Double getAverageOutputInputLengthRatio() {
		return averageOutputInputLengthRatio;
	}

	public Double getAverageBestRank() {
		return averageBestRank;
	}

	public Double getAverageBestRankInPercentage() {
		return averageBestRankInPercentage;
	}

	public Double getRelativeAverageBestRankInPercentage() {
		return relativeAverageBestRankInPercentage;
	}
	
}
