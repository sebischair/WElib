package de.tum.in.wwwmatthes.stm.models;

import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelTfidf extends ModelImpl {

	// Variables
	private TfidfVectorizer tfidfVectorizer;

	ModelTfidf(Config config) {
		super(config);
			
		tfidfVectorizer = new TfidfVectorizer.Builder()
			.setMinWordFrequency(config.getMinWordFrequency())
			.setStopWords(config.getTotalStopWords())
			.setTokenizerFactory(tokenizerFactory)
		 	.setIterator(documentsLabelAwareIterator)
		    	.build();
	}
	
	@Override
	public void fit() {
		tfidfVectorizer.fit();
				
		// Update documents look up table
		updateDocumentsLookupTable();
	}
	
	@Override
	public INDArray vectorFromText(String text) {
		return tfidfVectorizer.transform(text);
	}
	
}
