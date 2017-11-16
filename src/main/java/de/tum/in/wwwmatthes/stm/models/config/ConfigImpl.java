package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;

public class ConfigImpl implements Config {
	
	private String			type;
	private String			identifier;
	
	private List<String> 	stopWords				;
	private Boolean			addDefaultStopWords		;
	private Integer			minWordFrequency			;
	private String			documentsSourcePath;	
	
	private Boolean			preprocessingEnabled		;
	private Boolean			stemmingEnabled			;
	private List<String>		allowedPosTags			;
		
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
	public Boolean isAddDefaultStopWords() {
		return addDefaultStopWords;
	}
	public void setAddDefaultStopWords(Boolean addDefaultStopWords) {
		this.addDefaultStopWords = addDefaultStopWords;
	}
	@Override
	public List<String> getTotalStopWords() {
		List<String> totalStopWords = new ArrayList<String>(stopWords);
		if (addDefaultStopWords) {
			totalStopWords.addAll(StopWords.getStopWords());
		}
		return totalStopWords;
	}
	public Integer getMinWordFrequency() {
		return minWordFrequency;
	}
	public void setMinWordFrequency(Integer minWordFrequency) {
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
	
	public File getCorpusFile() {
		return null;
	}
	
	public void setStemmingEnabled(Boolean stemmingEnabled) {
		this.stemmingEnabled = stemmingEnabled;
	}

	public void setPreprocessingEnabled(Boolean preprocessingEnabled) {
		this.preprocessingEnabled = preprocessingEnabled;
	}

	@Override
	public List<String> getAllowedPosTags() {
		return allowedPosTags;
	}

	public void setAllowedPosTags(List<String> allowedPosTags) {
		this.allowedPosTags = allowedPosTags;
	}
	
	@Override
	public Integer getBatchSize() {
		return -1;
	}
	
	@Override
	public Integer getIterations() {
		return -1;
	}
	
	@Override
	public Double getLearningRate() {
		return -1.0;
	}
	
	@Override
	public Double getMinLearningRate() {
		return -1.0;
	}
	
	@Override
	public Double getSampling() {
		return -1.0;
	}

	@Override
	public Double getNegativeSample() {
		return -1.0;
	}
	
	public Integer getEpochs() {
		return -1;
	}

	public Integer getLayerSize() {
		return -1;
	}

	public Integer getWindowSize() {
		return -1;
	}

	@Override
	public String toString() {
		return "ConfigImpl [type=" + type + ", identifier=" + identifier + ", stopWords=" + stopWords
				+ ", addDefaultStopWords=" + addDefaultStopWords + ", minWordFrequency=" + minWordFrequency
				+ ", documentsSourcePath=" + documentsSourcePath + ", useStemming=" + stemmingEnabled + ", allowedPosTags="
				+ allowedPosTags + "]";
	}

	@Override
	public Boolean isPreprocessingEnabled() {
		return preprocessingEnabled;
	}

	@Override
	public Boolean isStemmingEnabled() {
		return stemmingEnabled;
	}
	
}
