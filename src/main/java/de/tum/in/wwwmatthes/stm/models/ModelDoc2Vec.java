package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.FileNotFoundException;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.plot.BarnesHutTsne;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.UimaSentenceIterator;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.exception.ND4JIllegalStateException;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelDoc2Vec extends ModelImpl {
	
	// Variables
	private ParagraphVectors 	vectors;
	private SentenceIterator 	corpusSentenceIterator;
	
	ModelDoc2Vec(Config config) {
		super(config);
		
		ParagraphVectors.Builder builder = new ParagraphVectors.Builder();

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
        			//.trainElementsRepresentation(true)
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
		
		// Create Documents Lookup Table
		updateDocumentsLookupTable();
	}

	@Override
	public INDArray vectorFromText(String text) throws VocabularyMatchException {
		try {
			return vectors.inferVector(text);
		} catch(ND4JIllegalStateException exception) {
			throw new VocabularyMatchException();
		}
	}

}
