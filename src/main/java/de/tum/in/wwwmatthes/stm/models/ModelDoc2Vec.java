package de.tum.in.wwwmatthes.stm.models;

import java.io.FileNotFoundException;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.config.Config;

class ModelDoc2Vec extends ModelImpl {
	
	// Variables
	private ParagraphVectors 	vectors;
	private SentenceIterator 	corpusSentenceIterator;
	private LabelAwareIterator 	corpusLabelAwareIterator;
	
	ModelDoc2Vec(Config config) {
		super(config);
		
		ParagraphVectors.Builder builder = new ParagraphVectors.Builder();

		if(config.getCorpusSourceFile() != null && config.getCorpusSourceFile().exists()) {
			corpusLabelAwareIterator = new FileLabelAwareIterator.Builder()
		              .addSourceFolder(config.getCorpusSourceFile())
		              .build();
			builder.iterate(corpusLabelAwareIterator);
			
		} else if(config.getCorpusFile() != null && config.getCorpusFile().exists()) {
			try {
				corpusSentenceIterator = new BasicLineIterator(config.getCorpusFile());
				builder.iterate(corpusSentenceIterator);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		vectors = builder
				.stopWords(config.getStopWords())
        			.batchSize(1000)
        			.epochs(config.getEpochs())
				.trainWordVectors(true)
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
	public INDArray vectorFromText(String text) {
		return vectors.inferVector(text);
	}

}
