package de.tum.in.wwwmatthes.stm.preprocessing;

import java.util.List;

public class StopWords {
	
	public static List<String> getStopWords() {
		if (list == null) {
			List<String> newList = org.deeplearning4j.text.stopwords.StopWords.getStopWords();
			newList.add("shall");
			
			list = newList;
		}
		return list;

	}
	
	private static List<String> list = null;

}
