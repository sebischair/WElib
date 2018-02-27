package de.tum.in.wwwmatthes.stm.models.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ConfigType {
	TFIDF, WORD2VEC, WORD2VEC_TFIDF, DOC2VEC;
	
	public static List<ConfigType> list() {
		return Arrays.asList(TFIDF, WORD2VEC, DOC2VEC);
	}
	
	public static List<String> stringList() {
		List<String> stringList = new ArrayList<String>();
		for(ConfigType type : list()) {
			stringList.add(type.toString());
		}
		return stringList;
	}
	
	static ConfigType typeFromString(String string) {
		if (string != null) {
			if (string.equalsIgnoreCase("tfidf")) {
				return ConfigType.TFIDF;
			} else if (string.equalsIgnoreCase("word2vec")) {
				return ConfigType.WORD2VEC;
			} else if (string.equalsIgnoreCase("word2vecTfidf")) {
				return ConfigType.WORD2VEC_TFIDF;
			} else if (string.equalsIgnoreCase("doc2vec")) {
				return ConfigType.DOC2VEC;
			}
		}
		return null;
	}

	static String stringFromType(ConfigType type) {
		return type.toString();
	}
	
	@Override
	public String toString() {
		if (this == ConfigType.TFIDF) {
			return "tfidf";
		} else if (this == ConfigType.WORD2VEC) {
			return "word2vec";
		} else if (this == ConfigType.WORD2VEC_TFIDF) {
			return "word2vecTfidf";
		} else if (this == ConfigType.DOC2VEC) {
			return "doc2vec";
		}
		return null;
	}

}
