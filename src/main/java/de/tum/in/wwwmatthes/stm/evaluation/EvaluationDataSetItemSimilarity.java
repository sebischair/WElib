package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.primitives.Pair;

public class EvaluationDataSetItemSimilarity {
	
	private String 	document;
	private Double 	similarity;
	private Integer 	documentLength;
	private Double 	documentInputRatio;
	
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

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public Integer getDocumentLength() {
		return documentLength;
	}

	public void setDocumentLength(Integer documentLength) {
		this.documentLength = documentLength;
	}

	public Double getDocumentInputRatio() {
		return documentInputRatio;
	}

	public void setDocumentInputRatio(Double documentInputRatio) {
		this.documentInputRatio = documentInputRatio;
	}

}
