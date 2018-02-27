package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Config {
	
	// Getters
	
	public String getIdentifier();
	
	public ConfigType getType();
	
	public List<String> getStopWords();
		
	public List<String> getTotalStopWords();
	
	public Boolean isAddDefaultStopWords();
	
	public Boolean isPreprocessingEnabled();
	public Boolean isStemmingEnabled();
	
	public List<String> getAllowedPosTags();

	public Integer getMinWordFrequency();
		
	public File getCorpusFile();
	public File getCorpusSourceFile();

	public Integer getIterations();
	
	public Integer getBatchSize();
	
	public Integer getEpochs();
	
	public Integer getWindowSize();
	
	public Integer getLayerSize();
	
	public Double getLearningRate();
	
	public Double getMinLearningRate();
	
	public Double getSampling();
	
	public Double getNegativeSample();
	
	// Methods
	
	public void writeToFile(File file) throws IOException;

}
