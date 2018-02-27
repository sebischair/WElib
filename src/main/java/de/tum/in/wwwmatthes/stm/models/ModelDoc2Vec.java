package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.FileSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.UimaSentenceIterator;
import org.deeplearning4j.text.uima.UimaResource;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.util.meansbuilder.DefaultMeansBuilder;

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
	public void fit() throws VocabularyMatchException {
		// Fit Model
		vectors.fit();		
	}

	@Override
	public INDArray vectorFromText(String text) throws VocabularyMatchException {	
		try {
			return vectors.inferVector(text);
		} catch (org.nd4j.linalg.exception.ND4JIllegalStateException exception) {
			throw new VocabularyMatchException(text);
		}
	}
	
	@Override
	protected void writeModel(File file) {
		if (vectors != null) {
			WordVectorSerializer.writeParagraphVectors(vectors, file);
		} else {
			log.error("Vectors null");
		}
	}
	
	@Override
	protected void readModel(File modelFile, Config config) throws Exception {
		
		ParagraphVectors vectors = WordVectorSerializer.readParagraphVectors(modelFile);
		vectors.setTokenizerFactory(tokenizerFactory);

		this.vectors			= vectors;
	}

}
