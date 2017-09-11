package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;
import de.tum.in.wwwmatthes.stm.util.FileUtil;

public class ConfigFactory {
	
	private String 		identifier;
	private ConfigType	type;
	
	private List<String>	stopWords				= new ArrayList<String>();
	private boolean		addDefaultStopWords		= true;			
	private int 			minWordFrequency			= 0;
	
	private File 		documentsSourceFile		= null;
		
	// Word2Vec & Doc2Vec
	private int	layerSize 	= 1;
	private int	windowSize 	= 2;
	private int	epochs 		= 0;
	
	private File corpusSourceFile 	= null;
	private File corpusFile 			= null;
	
	
	public static Config buildFromFile(File file) throws IOException, InvalidConfigException {
		String json 		= FileUtil.readFile(file);
		Config config 	= new Gson().fromJson(json, ConfigImpl.class);
		
		switch (config.getType()) {
		case TFIDF:
			config = new Gson().fromJson(json, ConfigTFIDF.class);
			break;
			
		case WORD2VEC:
			config = new Gson().fromJson(json, ConfigWord2Vec.class);
			break;
			
		case DOC2VEC:
			config = new Gson().fromJson(json, ConfigDoc2Vec.class);
			break;

		default:
			throw new InvalidConfigException();
		}
		
		return config;
	}
	
	public ConfigFactory(ConfigType type) {
		this.type = type;
	}
	
	public ConfigFactory useIdentifier(String identifier) {
		this.identifier = identifier;
		return this;
	}
	
	public ConfigFactory addDefaultStopWords(boolean addDefaultStopWords) {
		this.addDefaultStopWords = addDefaultStopWords;
		return this;
	}
	
	public ConfigFactory minWordFrequency(int minWordFrequency) {
		this.minWordFrequency = minWordFrequency;
		return this;
	}
	
	public ConfigFactory documentsSourceFile(File documentsSourceFile) {
		this.documentsSourceFile = documentsSourceFile;
		return this;
	}
	
	public ConfigFactory corpusSourceFile(File corpusSourceFile) {
		this.corpusSourceFile = corpusSourceFile;
		return this;
	}
	
	public ConfigFactory corpusFile(File corpusFile) {
		this.corpusFile = corpusFile;
		return this;
	}
	
	public ConfigFactory windowSize(int windowSize) {
		this.windowSize = windowSize;
		return this;
	}
	
	public ConfigFactory layerSize(int layerSize) {
		this.layerSize = layerSize;
		return this;
	}
	
	public ConfigFactory epochs(int epochs) {
		this.epochs = epochs;
		return this;
	}

	public Config build() throws InvalidConfigException {
				
		if (type == null) {
			throw new InvalidConfigException();
		}
		
		switch (type) {
		
		case TFIDF:
			ConfigTFIDF configTFIDF = new ConfigTFIDF();
			configTFIDF.setIdentifier(identifier);
			configTFIDF.setMinWordFrequency(minWordFrequency);
			configTFIDF.setStopWords(stopWords);
			configTFIDF.setDocumentsSourceFile(documentsSourceFile);
			configTFIDF.setAddDefaultStopWords(addDefaultStopWords);
			
			return configTFIDF;
			
		case WORD2VEC:
			ConfigWord2Vec configWORD2VEC = new ConfigWord2Vec();
			configWORD2VEC.setIdentifier(identifier);
			configWORD2VEC.setMinWordFrequency(minWordFrequency);
			configWORD2VEC.setStopWords(stopWords);
			configWORD2VEC.setDocumentsSourceFile(documentsSourceFile);
			configWORD2VEC.setAddDefaultStopWords(addDefaultStopWords);
			configWORD2VEC.setEpochs(epochs);
			configWORD2VEC.setCorpusSourceFile(corpusSourceFile);
			configWORD2VEC.setCorpusFile(corpusFile);
			
			return configWORD2VEC;
			
		case DOC2VEC:
			ConfigDoc2Vec configDOC2VEC = new ConfigDoc2Vec();
			configDOC2VEC.setIdentifier(identifier);
			configDOC2VEC.setMinWordFrequency(minWordFrequency);
			configDOC2VEC.setStopWords(stopWords);
			configDOC2VEC.setDocumentsSourceFile(documentsSourceFile);
			configDOC2VEC.setAddDefaultStopWords(addDefaultStopWords);
			configDOC2VEC.setEpochs(epochs);
			configDOC2VEC.setCorpusSourceFile(corpusSourceFile);
			configDOC2VEC.setCorpusFile(corpusFile);
			
			return configDOC2VEC;

		default:
			throw new InvalidConfigException();
		}
	}
	
}
