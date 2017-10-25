package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.primitives.Pair;

public class DataSetItemSimilarity {
	
	private String document;
	private Double similarity;
	
	public DataSetItemSimilarity(String document, Double similarity) {
		this.document 	= document;
		this.similarity 	= similarity;
	}
	
	public DataSetItemSimilarity(Pair<String, Double> pair) {
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
	
	public static List<DataSetItemSimilarity> createListFromPairMap(List<Pair<String, Double>> pairs) {
		List<DataSetItemSimilarity> similarities = new ArrayList<DataSetItemSimilarity>();
		for(Pair<String, Double> pair : pairs) {
			if (pair.getValue() != null) {
				similarities.add(new DataSetItemSimilarity(pair));
			}
		}
		return similarities;
	}

	@Override
	public String toString() {
		return "DataSetItemSimilarity [document=" + document + ", similarity=" + similarity + "]";
	}

}
