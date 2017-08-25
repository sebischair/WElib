package de.tum.in.wwwmatthes.stm.models;

import java.io.File;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.base.ModelImpl;
import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;

public class ModelDoc2Vec extends ModelImpl {
	
	// Variables
	private ParagraphVectors 	vectors;
	private LabelAwareIterator 	corpusLabelAwareIterator;
	
	public ModelDoc2Vec(File documentsSourceFile, File corpusSourceFile) {
		super(documentsSourceFile);
		
		corpusLabelAwareIterator = new FileLabelAwareIterator.Builder()
	              .addSourceFolder(corpusSourceFile)
	              .build();
		
		vectors = new ParagraphVectors.Builder()
				.stopWords(StopWords.getStopWords())
	        		.batchSize(1000)
	        		.epochs(20)
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
		this.updateDocumentsLookupTable();
	}

	@Override
	public INDArray vectorFromText(String text) {
		return vectors.inferVector(text);
	}

}
