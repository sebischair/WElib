package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.FileSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelWord2Vec extends ModelImpl {
	
	// Variables
	private Word2Vec 			vectors;
	private SentenceIterator 	corpusSentenceIterator;
	
	ModelWord2Vec(Config config) {
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

        			.seed(42)
        			.allowParallelTokenization(false)
	        		.tokenizerFactory(tokenizerFactory)
	        		.build();
	}

	@Override
	public void fit() throws VocabularyMatchException {
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
        
        List<VocabWord> words = getVocabWords(tokens);
        if(words.size() > 0) {
        		INDArray result = vectors.getWordVectorMatrix(words.get(0).getWord());
        		for(int i = 1; i < words.size(); i++) {
        			result.add(vectors.getWordVectorMatrix(words.get(0).getWord()));
        		}
        		return result;
        }
        
		return null;
	}
	
	// Private Helper Methods
	
	private List<VocabWord> getVocabWords(List<String> tokens) {
		List<VocabWord> words = new ArrayList<VocabWord>();
		for(String token : tokens) {
	 		VocabWord word = vocab.tokenFor(token);
	 		if(word != null) {
	 			words.add(word);
    			}
		}
		return words;
	}

}
