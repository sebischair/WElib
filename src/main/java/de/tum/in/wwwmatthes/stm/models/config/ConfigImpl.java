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
	private boolean			addDefaultStopWords		;
	private int				minWordFrequency			;
	private String			documentsSourcePath;	
	
	private boolean			preprocessingEnabled		;
	private boolean			stemmingEnabled			;
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
	public boolean isAddDefaultStopWords() {
		return addDefaultStopWords;
	}
	public void setAddDefaultStopWords(boolean addDefaultStopWords) {
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
	
	public File getCorpusFile() {
		return null;
	}
	
	public void setStemmingEnabled(boolean stemmingEnabled) {
		this.stemmingEnabled = stemmingEnabled;
	}

	public boolean isPreprocessingEnabled() {
		return preprocessingEnabled;
	}

	public void setPreprocessingEnabled(boolean preprocessingEnabled) {
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
	public int getBatchSize() {
		return -1;
	}
	
	@Override
	public int getIterations() {
		return -1;
	}
	
	@Override
	public double getLearningRate() {
		return -1;
	}
	
	@Override
	public double getMinLearningRate() {
		return -1;
	}
	
	@Override
	public double getSampling() {
		return -1;
	}

	@Override
	public double getNegativeSample() {
		return -1;
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

	@Override
	public String toString() {
		return "ConfigImpl [type=" + type + ", identifier=" + identifier + ", stopWords=" + stopWords
				+ ", addDefaultStopWords=" + addDefaultStopWords + ", minWordFrequency=" + minWordFrequency
				+ ", documentsSourcePath=" + documentsSourcePath + ", useStemming=" + stemmingEnabled + ", allowedPosTags="
				+ allowedPosTags + "]";
	}

	@Override
	public boolean isProcessingEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStemmingEnabled() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
