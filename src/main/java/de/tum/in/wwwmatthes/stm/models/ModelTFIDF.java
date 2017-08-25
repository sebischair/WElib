package de.tum.in.wwwmatthes.stm.models;

import java.io.File;

import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.base.ModelImpl;
import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;

public class ModelTFIDF extends ModelImpl {

	// Variables
	private TfidfVectorizer tfidfVectorizer;

	public ModelTFIDF(File documentsSourceFile) {
		super(documentsSourceFile);
		
		tfidfVectorizer = new TfidfVectorizer.Builder()
			//.setMinWordFrequency(2)
			.setStopWords(StopWords.getStopWords())
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
