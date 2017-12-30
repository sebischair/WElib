package de.tum.in.wwwmatthes.stm.models;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.FileSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.util.MathUtils;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.util.meansbuilder.MeansBuilder;
import de.tum.in.wwwmatthes.stm.util.meansbuilder.TfidfMeansBuilder;

class ModelWord2VecTfidf extends ModelImpl {

	// Variables
	private Word2Vec 			word2VecVectors;
	private TfidfVectorizer		tfidfVectors;
	
	private MeansBuilder 		meansBuilder;
	
	ModelWord2VecTfidf(Config config) {
		super(config);
		
		Word2Vec.Builder builder = new Word2Vec.Builder();
		
		if (config.getCorpusFile() != null) {
			try {				
				SentenceIterator corpusSentenceIterator = new BasicLineIterator(config.getCorpusFile());
				builder.iterate(corpusSentenceIterator);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		} else if (config.getCorpusSourceFile() != null) {
			FileSentenceIterator corpusSentenceIterator = new FileSentenceIterator(config.getCorpusSourceFile());
			builder.iterate(corpusSentenceIterator);
		}
		
		word2VecVectors = builder
				
				.minWordFrequency(config.getMinWordFrequency())
				.stopWords(config.getTotalStopWords())
        			.epochs(config.getEpochs())
        			.iterations(config.getIterations())
        			.batchSize(config.getBatchSize())
        			.layerSize(config.getLayerSize())
        			.windowSize(config.getWindowSize())
        			.learningRate(config.getLearningRate())
        			.minLearningRate(config.getMinLearningRate())
        			.sampling(config.getSampling())
        			.negativeSample(config.getNegativeSample())

        			.seed(42)
        			.allowParallelTokenization(false)
	        		.tokenizerFactory(tokenizerFactory)
	        		.build();
		
		// Tfidf
		tfidfVectors = new TfidfVectorizer.Builder()
				.setMinWordFrequency(config.getMinWordFrequency())
				.setStopWords(config.getTotalStopWords())
				.setTokenizerFactory(tokenizerFactory)
			 	.setIterator(documentsLabelAwareIterator)
			    	.build();
	}

	@Override
	public void fit() throws VocabularyMatchException {
		
		// Fit Model
		word2VecVectors.fit();
		tfidfVectors.fit();
				
		// Set Vocab
		vocab = word2VecVectors.getVocab();
		
		//vectors.getLookupTable().plotVocab(100, new File("/Users/christopherl/Desktop/test.plot"));
		System.out.println(word2VecVectors.getConfiguration());
		System.out.println(word2VecVectors.wordsNearest("day", 20));
	
		// Setup Means Builder
		meansBuilder = new TfidfMeansBuilder(word2VecVectors.getLookupTable(), tokenizerFactory, tfidfVectors);
		
		// Create Documents Lookup Table
		updateDocumentsLookupTable();
	}

	@Override
	public INDArray vectorFromText(String text) {
		return meansBuilder.transformString(text);
	}
    
}
