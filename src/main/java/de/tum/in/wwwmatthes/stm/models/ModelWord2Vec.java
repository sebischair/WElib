package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
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

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.exceptions.VocabularyMatchException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigFactory;
import de.tum.in.wwwmatthes.stm.util.meansbuilder.DefaultMeansBuilder;
import de.tum.in.wwwmatthes.stm.util.meansbuilder.MeansBuilder;

class ModelWord2Vec extends ModelImpl {
	
	// Variables
	private Word2Vec 			vectors;
	private MeansBuilder 		meansBuilder;
	
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
	
		// Setup Means Builder
		meansBuilder = new DefaultMeansBuilder(vectors.getLookupTable(), tokenizerFactory);
		
		// Create Documents Lookup Table
		updateDocumentsLookupTable();
	}

	@Override
	public INDArray vectorFromText(String text) {
		return meansBuilder.transformString(text);
	}
	
	@Override
	protected void writeModel(File file) {
		if (vectors != null) {
			WordVectorSerializer.writeWord2VecModel(vectors, file);
		} else {
			log.error("Vectors null");
		}
	}
	
	@Override
	protected void readModel(File modelFile, Config config) throws Exception {
		
		Word2Vec vectors 	= WordVectorSerializer.readWord2VecModel(modelFile);

		this.vectors			= vectors;
		this.vocab 			= vectors.getVocab();
		this.meansBuilder	= new DefaultMeansBuilder(vectors.getLookupTable(), tokenizerFactory);
		
		// Create Documents Lookup Table
		this.updateDocumentsLookupTable();
	}
		
}
