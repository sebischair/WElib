package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

public class ConfigImpl implements Config {
	
	private String			type;
	private String			identifier;
	
	private List<String> 	stopWords;
	private boolean			addDefaultStopWords;
	private int				minWordFrequency;
	private String			documentsSourcePath;	
		
	public ConfigImpl(ConfigType type) {
		super();
		this.setType(type);
	}
	
	/*
	 * Methods
	 */
	
	public void writeToFile(File file) throws IOException {
		String json = new Gson().toJson(this);
		FileUtils.writeStringToFile(file, json);
	}
	
	/*
	 * Getters & Setters
	 */
	
	public ConfigType getType() {
		return ConfigType.typeFromString(type);
	}

	public void setType(ConfigType type) {
		this.type = ConfigType.stringFromType(type);
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public List<String> getStopWords() {
		return stopWords;
	}
	public void setStopWords(List<String> stopWords) {
		this.stopWords = stopWords;
	}
	public boolean isAddDefaultStopWords() {
		return addDefaultStopWords;
	}
	public void setAddDefaultStopWords(boolean addDefaultStopWords) {
		this.addDefaultStopWords = addDefaultStopWords;
	}
	public int getMinWordFrequency() {
		return minWordFrequency;
	}
	public void setMinWordFrequency(int minWordFrequency) {
		this.minWordFrequency = minWordFrequency;
	}
	
	public File getDocumentsSourceFile() {
		if(documentsSourcePath!=null) {
			return new File(documentsSourcePath);
		}
		return null;
	}

	public void setDocumentsSourceFile(File documentsSourceFile) {
		if(documentsSourceFile!=null) {
			this.documentsSourcePath = documentsSourceFile.getPath();
		} else {
			this.documentsSourcePath = null;
		}
	}
	
	public int getEpochs() {
		return -1;
	}

	public int getLayerSize() {
		return -1;
	}

	public int getWindowSize() {
		return -1;
	}

	public File getCorpusFile() {
		return null;
	}

	public File getCorpusSourceFile() {
		return null;
	}
	
}
