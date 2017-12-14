package de.tum.in.wwwmatthes.stm.evaluation;

import org.nd4j.linalg.primitives.Pair;

public class EvaluationDataSetItemSimilarity {
	
	private String 	document;
	private Double 	similarity;
	private Integer 	documentLength;
	private Double 	documentInputLengthRatio;
	
	public EvaluationDataSetItemSimilarity(String document, Double similarity) {
		this.document 	= document;
		this.similarity 	= similarity;
	}
	
	public EvaluationDataSetItemSimilarity(Pair<String, Double> pair) {
		this(pair.getKey(), pair.getValue());
	}

	public String getDocument() {
		return document;
	}
	
	public void setDocument(String document) {
		this.document = document;
	}

	public Integer getDocumentLength() {
		return documentLength;
	}

	public void setDocumentLength(Integer documentLength) {
		this.documentLength = documentLength;
	}

	public Double getDocumentInputLengthRatio() {
		return documentInputLengthRatio;
	}

	public void setDocumentInputLengthRatio(Double documentInputLengthRatio) {
		this.documentInputLengthRatio = documentInputLengthRatio;
	}

	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}

	public Double getSimilarity() {
		return similarity;
	}

	@Override
	public String toString() {
		return "EvaluationDataSetItemSimilarity [document=" + document + ", similarity=" + similarity
				+ ", documentLength=" + documentLength + ", documentInputLengthRatio=" + documentInputLengthRatio + "]";
	}
	
}
