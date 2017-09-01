package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;
import de.tum.in.wwwmatthes.stm.util.FileUtil;

abstract class ConfigImpl implements Config {
	
	// General
	protected final String 		key;
	protected final ConfigType	type;
	
	protected List<String> 	stopWords				= new ArrayList<String>();
	protected boolean		useDefaultStopWords		= true;
	protected int 			minWordFrequency			= 0;
	
	protected File 			documentsSourceFile		= null;
	
	// Word2Vec & Doc2Vec
	private int	layerSize 	= 0;
	private int	windowSize 	= 0;
	private int	epochs 		= 0;
	
	private File corpusSourceFile = null;
	
	// Config File
	private File 		configFile;
	
	/**
	 * Create a new object of type Config.
	 * @param key Key associated with this configuration.
	 * @param type Type of this configuration - TFIDF, Word2Vec etc..
	 */
	ConfigImpl(String key, ConfigType type) {
		super();
		
		this.key 	= key; 
		this.type 	= type;
	}
	
	/*
	 *  ConfigAPI
	 */
	
	public ConfigType getType() {
		return type;
	}
	
	public List<String> getStopWords() {
		return stopWords;
	}

	public int getMinWordFrequency() {
		return minWordFrequency;
	}

	public File getDocumentsSourceFile() {
		return documentsSourceFile;
	}

	public boolean isUseDefaultStopWords() {
		return useDefaultStopWords;
	}

	public File getCorpusSourceFile() {
		return corpusSourceFile;
	}

	public int getEpochs() {
		return epochs;
	}

	public String getKey() {
		return key;
	}

	public int getLayerSize() {
		return layerSize;
	}

	public int getWindowSize() {
		return windowSize;
	}

	/*
	 * Setters
	 */
	
	void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	
	void setMinWordFrequency(int minWordFrequency) {
		this.minWordFrequency = minWordFrequency;
	}
	
	void setLayerSize(int layerSize) {
		this.layerSize = layerSize;
	}
	
	void setEpochs(int epochs) {
		this.epochs = epochs;
	}
	
	void setCorpusSourceFile(File corpusSourceFile) {
		this.corpusSourceFile = corpusSourceFile;
	}
	
	void setUseDefaultStopWords(boolean useDefaultStopWords) {
		this.useDefaultStopWords = useDefaultStopWords;
	}
	
	void setDocumentsSourceFile(File documentsSourceFile) {
		this.documentsSourceFile = documentsSourceFile;
	}
	
	void setStopWords(List<String> stopWords) {
		this.stopWords = stopWords;
	}

	@Override
	public String toString() {
		return "Config [key=" + key + ", type=" + type + ", stopWords=" + stopWords + ", minWordFrequency="
				+ minWordFrequency + "]";
	}

}
