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
        			.negativeSample(config.getMinLearningRate())

        			.seed(42)
        			.allowParallelTokenization(false)
	        		.tokenizerFactory(tokenizerFactory)
	        		.build();
	}

	@Override
	public void fit() {
		// Fit Model
		vectors.fit();
		
		// Set Vocab
		vocab = vectors.getVocab();
		
		//vectors.getLookupTable().plotVocab(100, new File("/Users/christopherl/Desktop/test.plot"));
		System.out.println(vectors.getConfiguration());
		System.out.println(vectors.wordsNearest("day", 20));
	
		// Create Documents Lookup Table
		updateDocumentsLookupTable();
	}

	@Override
	public INDArray vectorFromText(String text) {
        Tokenizer tokenizer = tokenizerFactory.create(text);
        List<String> tokens = tokenizer.getTokens();
        
        //System.out.println(tokens.size());
        
        INDArray vector = new org.nd4j.linalg.api.ndarray.BaseNDArray(1, 100) {

			@Override
			public INDArray unsafeDuplication() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public INDArray unsafeDuplication(boolean blocking) {
				// TODO Auto-generated method stub
				return null;
			} 
        	
        };
        //INDArray vector = null; //new org.nd4j.linalg.api.ndarray.BaseNDArray(new float[]()) {0.0, 0.0}); 
        for(String token : tokens) {
        		INDArray tokenVector = vectors.getWordVectorMatrix(token);
        		if(vector == null && tokenVector != null) {
        			vector = tokenVector;
        		} else if (tokenVector != null) {
        			vector.add(tokenVector);
        		}
        		if(tokenVector != null) {
            		//System.out.println("is Matrix: " + tokenVector.isMatrix());
            		//System.out.println("Length: " + tokenVector.length());
            		//System.out.println("Rows: " + tokenVector.rows());
        		} else {
        			//System.out.println("tokenVector: " + null);
        		}
        }
        
        if (tokens.size() == 16) {
	        INDArray vector1 = null; 
	        for(String token : tokens) {
	        		//System.out.println("Token: " + token);
	        		
	        		INDArray tokenVector = vectors.getWordVectorMatrix(token);
	        		//System.out.println("Token Vector: " + tokenVector);
	        		if(vector1 == null && tokenVector != null) {
	        			vector1 = tokenVector;
	        		} else if (tokenVector != null) {
	        			vector1.add(tokenVector);
	        		}
	        }
	        //System.out.println(vector1);
	        return vector1;
		}
        
		return vector;
	}

}
