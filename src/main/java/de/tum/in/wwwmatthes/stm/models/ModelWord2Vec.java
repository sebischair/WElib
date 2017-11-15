package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelWord2Vec extends ModelImpl {
	
	// Variables
	private Word2Vec 			vectors;
	private SentenceIterator 	corpusSentenceIterator;
	
	ModelWord2Vec(Config config) {
		super(config);
		
		Word2Vec.Builder builder = new Word2Vec.Builder();
		
		try {
			corpusSentenceIterator = new BasicLineIterator(config.getCorpusFile());
			builder.iterate(corpusSentenceIterator);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		vectors = builder
				
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
        			
        			.allowParallelTokenization(false)
	        		.tokenizerFactory(tokenizerFactory)
	        		.seed(42)

	        		.build();
	}

	@Override
	public void fit() {
		// Fit Model
		vectors.fit();
		
		//vectors.getLookupTable().plotVocab(100, new File("/Users/christopherl/Desktop/test.plot"));
		System.out.println(vectors.getConfiguration());
		System.out.println(vectors.wordsNearest("data", 20));
	
		// Create Documents Lookup Table
		updateDocumentsLookupTable();
	}

	@Override
	public INDArray vectorFromText(String text) {
        Tokenizer tokenizer = tokenizerFactory.create(text);
        List<String> tokens = tokenizer.getTokens();
        
        INDArray vector = null; 
        for(String token : tokens) {
        		INDArray tokenVector = vectors.getWordVectorMatrix(token);
        		if(vector == null && tokenVector != null) {
        			vector = tokenVector;
        		} else if (tokenVector != null) {
        			vector.add(tokenVector);
        		}
        }
        
		return vector;
	}

}
