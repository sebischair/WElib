package de.tum.in.wwwmatthes.stm.models;

import java.io.FileNotFoundException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.FileSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.UimaSentenceIterator;
import org.deeplearning4j.text.uima.UimaResource;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelDoc2Vec extends ModelImpl {
	
	// Variables
	private ParagraphVectors 	vectors;
	
	ModelDoc2Vec(Config config) {
		super(config);
		
		ParagraphVectors.Builder builder = new ParagraphVectors.Builder();
		
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
				.trainWordVectors(true)
        			.seed(42)
        			.allowParallelTokenization(false)
	        		.tokenizerFactory(tokenizerFactory)
	        		.build();
	}

	@Override
	public void fit() {
		// Fit Model
		vectors.fit();		
		System.out.println(vectors.wordsNearest("data", 20));
		
		// Set Vocab
		vocab = vectors.getVocab();
		
		// Create Documents Lookup Table
		updateDocumentsLookupTable();
	}

	@Override
	public INDArray vectorFromText(String text) {		
		return vectors.inferVector(text);	
	}

}
