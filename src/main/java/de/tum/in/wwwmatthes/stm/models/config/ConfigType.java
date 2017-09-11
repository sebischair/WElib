package de.tum.in.wwwmatthes.stm.models.config;

public enum ConfigType {
	TFIDF, WORD2VEC, DOC2VEC;

	static ConfigType typeFromString(String string) {
		if (string != null) {
			if (string.equalsIgnoreCase("tfidf")) {
				return ConfigType.TFIDF;
			} else if (string.equalsIgnoreCase("word2vec")) {
				return ConfigType.WORD2VEC;
			} else if (string.equalsIgnoreCase("doc2Vec")) {
				return ConfigType.DOC2VEC;
			}
		}
		return null;
	}

	static String stringFromType(ConfigType type) {
		if (type == ConfigType.TFIDF) {
			return "tfidf";
		} else if (type == ConfigType.WORD2VEC) {
			return "word2vec";
		} else if (type == ConfigType.DOC2VEC) {
			return "doc2Vec";
		}
		return null;
	}

}
