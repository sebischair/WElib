package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Config {
	
	// Getters
	
	public String getIdentifier();
	
	public ConfigType getType();
	
	public List<String> getStopWords();
	
	public Boolean isAddDefaultStopWords();
	
	public List<String> getTotalStopWords();
	
	public boolean isUseStemming();
	
	public List<String> getAllowedPosTags();

	public int getMinWordFrequency();
	
	public File getDocumentsSourceFile();
	
	public File getCorpusFile();

	public int getIterations();
	
	public int getBatchSize();
	
	public int getEpochs();
	
	public int getWindowSize();
	
	public int getLayerSize();
	
	public double getLearningRate();
	
	public double getMinLearningRate();
	
	public double getSampling();
	
	public double getNegativeSample();
	
	// Methods
	
	public void writeToFile(File file) throws IOException;

}
