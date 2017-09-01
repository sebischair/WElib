package de.tum.in.wwwmatthes.stm.models;

import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelTFIDF extends ModelImpl {

	// Variables
	private TfidfVectorizer tfidfVectorizer;

	ModelTFIDF(Config config) {
		super(config);
		
		tfidfVectorizer = new TfidfVectorizer.Builder()
			.setMinWordFrequency(config.getMinWordFrequency())
			.setStopWords(config.getStopWords())
			.setTokenizerFactory(tokenizerFactory)
		 	.setIterator(documentsLabelAwareIterator)
			.allowParallelTokenization(false)
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
