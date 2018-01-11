package de.tum.in.wwwmatthes.stm.models;

import java.io.File;

import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
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
	public void fit() throws VocabularyMatchException {
		// Fit Model
		tfidfVectorizer.fit();
		
		// Set Vocab
		vocab = tfidfVectorizer.getVocabCache();
				
		// Update documents look up table
		updateDocumentsLookupTable();
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
	
}
