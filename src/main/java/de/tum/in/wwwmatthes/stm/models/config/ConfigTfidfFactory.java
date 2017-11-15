package de.tum.in.wwwmatthes.stm.models.config;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;

public class ConfigTfidfFactory extends ConfigFactory {
	
	public ConfigTfidfFactory() {
		super(ConfigType.TFIDF);
	}
		
	@Override
	public Config build() throws InvalidConfigException {
				
		ConfigTfidf config = new ConfigTfidf();
		config.setIdentifier(identifier);
		config.setMinWordFrequency(minWordFrequency); 
		config.setPreprocessingEnabled(preprocessingEnabled);
		config.setStemmingEnabled(stemmingEnabled);
		config.setAllowedPosTags(allowedPosTags);
		config.setStopWords(stopWords);
		config.setDocumentsSourceFile(documentsSourceFile);
		config.setAddDefaultStopWords(addDefaultStopWords);
		
		return config;
	}
}
