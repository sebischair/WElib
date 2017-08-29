package de.tum.in.wwwmatthes.stm.models;

import java.io.File;

import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.documentiterator.FileLabelAwareIterator;
import org.deeplearning4j.text.documentiterator.LabelAwareIterator;
import org.nd4j.linalg.api.ndarray.INDArray;

import de.tum.in.wwwmatthes.stm.models.base.ModelImpl;
import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;

public class ModelWord2Vec extends ModelImpl {
	
	// Variables
	private Word2Vec 			vectors;
	private LabelAwareIterator 	corpusLabelAwareIterator;
	
	public ModelWord2Vec(File documentsSourceFile, File corpusSourceFile) {
		super(documentsSourceFile);
		
		corpusLabelAwareIterator = new FileLabelAwareIterator.Builder()
	              .addSourceFolder(corpusSourceFile)
	              .build();
		
		vectors = new Word2Vec.Builder()
				.stopWords(StopWords.getStopWords())
	        		.batchSize(1000)
	        		.epochs(20)
	        		.iterate(corpusLabelAwareIterator)
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
		return vectors.getWordVectorMatrixNormalized(""); // TODO
	}

}
