package de.tum.in.wwwmatthes.stm.models;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;

import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelDoc2Vec extends ModelImpl {
	
	// Variables
	private ParagraphVectors 	vectors;
	private LabelAwareIterator 	corpusLabelAwareIterator;
	
	ModelDoc2Vec(Config config) {
		super(config);
		
		corpusLabelAwareIterator = new FileLabelAwareIterator.Builder()
	              .addSourceFolder(config.getCorpusSourceFile())
	              .build();
		
		vectors = new ParagraphVectors.Builder()
				.stopWords(config.getStopWords())
	        		.batchSize(1000)
	        		.epochs(config.getEpochs())
	        		.iterate(corpusLabelAwareIterator)
	        		.trainWordVectors(true)
	        		.tokenizerFactory(tokenizerFactory)
	        		.windowSize(5)
	        		.layerSize(100)
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
		return vectors.inferVector(text);
	}

}
