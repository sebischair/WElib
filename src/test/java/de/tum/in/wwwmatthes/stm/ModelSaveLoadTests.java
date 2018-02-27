package de.tum.in.wwwmatthes.stm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.datavec.api.util.ClassPathResource;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.paragraphvectors.ParagraphVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.junit.Test;

import de.tum.in.wwwmatthes.stm.models.Model;
import de.tum.in.wwwmatthes.stm.models.ModelFactory;
import de.tum.in.wwwmatthes.stm.tokenizers.CustomTokenizerFactory;

public class ModelSaveLoadTests {
	
	@Test
	public void testPreprocessing() throws IOException {

		File file = new ClassPathResource("GoogleNews-vectors-negative300.bin.gz").getFile();
		
		//Word2Vec model = WordVectorSerializer.readWord2VecModel(file);
		ParagraphVectors model = WordVectorSerializer.readParagraphVectors(file); //readWord2VecModel(file);
		System.out.println("Works: " + model.wordsNearest("day", 20));
	}

}
