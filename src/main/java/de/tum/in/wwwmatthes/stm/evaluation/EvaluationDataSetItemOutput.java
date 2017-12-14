package de.tum.in.wwwmatthes.stm.evaluation;

public class EvaluationDataSetItemOutput {
	
	private Integer 	rank;
	private Double 	rankInPercentage;
	private Long 	outputLength;
	private Double 	outputInputLengthRatio;
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public Long getOutputLength() {
		return outputLength;
	}
	public void setOutputLength(Long outputLength) {
		this.outputLength = outputLength;
	}
	public Double getOutputInputLengthRatio() {
		return outputInputLengthRatio;
	}
	public void setOutputInputLengthRatio(Double outputInputLengthRatio) {
		this.outputInputLengthRatio = outputInputLengthRatio;
	}
		
	public Double getRankInPercentage() {
		return rankInPercentage;
	}
	public void setRankInPercentage(Double rankInPercentage) {
		this.rankInPercentage = rankInPercentage;
	}
	
	@Override
	public String toString() {
		return "EvaluationDataSetItemOutput [rank=" + rank + ", outputLength=" + outputLength
				+ ", outputInputLengthRatio=" + outputInputLengthRatio + "]";
	}

}
