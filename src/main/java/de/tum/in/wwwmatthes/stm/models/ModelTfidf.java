package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.text.documentiterator.DocumentIterator;
import org.deeplearning4j.text.sentenceiterator.CollectionSentenceIterator;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelTfidf extends ModelImpl {

	// Variables
	private TfidfVectorizer tfidfVectorizer;

	ModelTfidf(Config config) {
		super(config);
		
		fit(new ArrayList<String>());
	}
	
	@Override
	public void fit() throws VocabularyMatchException {
		// Nothing to do here
	}
	
	@Override
	public void putDocuments(Map<String, String> documents) throws VocabularyMatchException {
		
		Collection<String> collection = new ArrayList<String>();
		for(String key : documents.keySet()) {
			collection.add(documents.get(key));
		}
		
		fit(collection);
				
		// Super Put Documents
		super.putDocuments(documents);
	}
	
	@Override
	public INDArray vectorFromText(String text) throws VocabularyMatchException {
		INDArray vector = tfidfVectorizer.transform(text);
		// Vector may not be null and not only zeros
		if(vector == null || vector.amaxNumber().doubleValue() == 0) {
			throw new VocabularyMatchException(text);
		}
		return vector;
	}

	@Override
	protected void writeModel(File file) {
		
	}
	
	@Override
	protected void readModel(File modelFile, Config config) throws Exception {
		fit();
	}
	
	// Private Methods
	
	private void fit(Collection<String> documents) {
		tfidfVectorizer = new TfidfVectorizer.Builder()
				.setMinWordFrequency(config.getMinWordFrequency())
				.setStopWords(config.getTotalStopWords())
				.setTokenizerFactory(tokenizerFactory)
				.setIterator(new CollectionSentenceIterator(documents))
			    	.build();
		tfidfVectorizer.fit();
	}
	
}
