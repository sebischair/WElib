package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.util.FileUtil;

public class ConfigFactory {
	
	protected String 		identifier;
	protected ConfigType		type;
	
	protected Boolean		stemmingEnabled		= false;
	protected Boolean		preprocessingEnabled	= false;
	protected List<String>	allowedPosTags		;
	
	protected List<String>	stopWords			= new ArrayList<String>();
	protected Boolean		addDefaultStopWords	= false;			
	protected Integer 		minWordFrequency		= 0;
	
	protected ConfigFactory(ConfigType type) {
		this.type = type;
	}
	
	public ConfigFactory useIdentifier(String identifier) {
		this.identifier = identifier;
		return this;
	}
	
	public ConfigFactory allowedPosTags(List<String> allowedPosTags) {
		this.allowedPosTags = allowedPosTags;
		return this;
	}
	
	public ConfigFactory enableStemming(Boolean enableStemming) {
		this.stemmingEnabled = enableStemming;
		return this;
	}
	
	public ConfigFactory enablePreprocessing(Boolean enablePreprocessing) {
		this.preprocessingEnabled = enablePreprocessing;
		return this;
	}
	
	public ConfigFactory stopWords(List<String> stopWords) {
		this.stopWords = stopWords;
		return this;
	}
	
	public ConfigFactory addDefaultStopWords(Boolean addDefaultStopWords) {
		this.addDefaultStopWords = addDefaultStopWords;
		return this;
	}
	
	public ConfigFactory minWordFrequency(Integer minWordFrequency) {
		this.minWordFrequency = minWordFrequency;
		return this;
	}
	
	public Config build() throws InvalidConfigException {
		throw new InvalidConfigException();
	}
	
	// Public Static Methods
	
	public static Config buildFromFile(File file) throws IOException, InvalidConfigException {
		String json 		= FileUtil.readFile(file);
		Config config 	= new Gson().fromJson(json, ConfigImpl.class);
		
		switch (config.getType()) {
		case TFIDF:
			return new Gson().fromJson(json, ConfigTfidf.class);
			
		case WORD2VEC:
			return new Gson().fromJson(json, ConfigWord2Vec.class);
			
		case DOC2VEC:
			return new Gson().fromJson(json, ConfigDoc2Vec.class);

		default:
			throw new InvalidConfigException();
		}
	}
	
}
