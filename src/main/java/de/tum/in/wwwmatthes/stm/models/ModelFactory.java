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
			return new ModelTfidf(config);
			
		case WORD2VEC:
			return new ModelWord2Vec(config);
			
		case DOC2VEC:
			return new ModelDoc2Vec(config);

		default:
			return null;
		}
		
	}
	
	
	public static Model read(File file) throws Exception {
		
		Config config 	= ConfigFactory.buildFromFile(ModelImpl.getConfigFileFromModeFile(file));
		
		ModelImpl model 	= null;
		switch (config.getType()) {
		
		case TFIDF:
			model = new ModelTfidf(config);
			break;
			
		case WORD2VEC:
			model = new ModelWord2Vec(config);
			break;
			
		case DOC2VEC:
			model = new ModelDoc2Vec(config);
			break;

		default:
			return null;
		}
		
		model.read(file);
		return model;
	}

}
