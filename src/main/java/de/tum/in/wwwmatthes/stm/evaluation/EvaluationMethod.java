package de.tum.in.wwwmatthes.stm.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluationMethod {
	
	public static Map<String, Integer> ranks(List<String> relevantDocuments, List<String> rankedDocuments) {
		Map<String, Integer> rankMap = new HashMap<String, Integer>();
		
		for(String relevantDocument : relevantDocuments) {
			int rank = rankedDocuments.indexOf(relevantDocument) + 1;
			if (rank > 0) {
				rankMap.put(relevantDocument, rank);
			} else {
				rankMap.put(relevantDocument, rankedDocuments.size());
			}
		}
		return rankMap;
	}
	
	public static double mrrFromMap(Map<String, Integer> map) {
		List<Integer> ranks = new ArrayList<Integer>();
		for(String key : map.keySet()) {
			ranks.add(map.get(key));
		}
		return mrrFromList(ranks);
	}
	
	public static double mrrFromList(List<Integer> list) {
		double sum = 0;
		for(Integer rank : list) {
			sum += 1.0 / (double) rank;
		}
		return sum / list.size();
	}

}
