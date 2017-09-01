package de.tum.in.wwwmatthes.stm.models;

import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelWord2Vec extends ModelImpl {
	
	// Variables
	private Word2Vec 			vectors;
	private LabelAwareIterator 	corpusLabelAwareIterator;
	
	ModelWord2Vec(Config config) {
		super(config);
		
		corpusLabelAwareIterator = new FileLabelAwareIterator.Builder()
	              .addSourceFolder(config.getCorpusSourceFile())
	              .build();
		
		vectors = new Word2Vec.Builder()
				.stopWords(config.getStopWords())
	        		.batchSize(1000)
	        		.epochs(config.getEpochs())
	        		.iterate(corpusLabelAwareIterator)
	        		.tokenizerFactory(tokenizerFactory)
	        		.windowSize(config.getWindowSize())
	        		.layerSize(config.getLayerSize())
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
	public INDArray vectorFromText(String text) {
		return vectors.getWordVectorMatrixNormalized(""); // TODO
	}

}
