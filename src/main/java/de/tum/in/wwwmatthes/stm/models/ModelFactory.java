package de.tum.in.wwwmatthes.stm.models;

import java.io.File;
import java.io.IOException;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.models.config.Config;
import de.tum.in.wwwmatthes.stm.models.config.ConfigFactory;

public class ModelFactory {

	public static Model createFromConfigFile(File file) throws IOException, InvalidConfigException {
		Config config = ConfigFactory.buildFromFile(file);
		return createFromConfig(config);
	}
	
	public static Model createFromConfig(Config config) {
		
		switch (config.getType()) {
		
		case TFIDF:
			return new ModelTFIDF(config);
			
		case WORD2VEC:
			return new ModelWord2Vec(config);
			
		case DOC2VEC:
			return new ModelDoc2Vec(config);

		default:
			return null;
		}
		
	}

}
