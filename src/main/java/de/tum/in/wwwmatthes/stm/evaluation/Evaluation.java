package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.List;
import java.util.logging.Logger;

public class Evaluation {
	
	public static double mrr(List<String> relevantDocuments, List<String> rankedDocuments) {
		double sum = 0;
		for(String relevantDocument : relevantDocuments) {
			sum += 1.0 / (double) (rankedDocuments.indexOf(relevantDocument) + 1.0);
		}
		return sum / relevantDocuments.size();
	}
	
}
