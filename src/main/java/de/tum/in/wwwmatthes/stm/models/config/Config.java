package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Config {
	
	// Getters
	
	public String getIdentifier();
	
	public ConfigType getType();
	
	public List<String> getStopWords();
	
	public boolean isAddDefaultStopWords();

	public int getMinWordFrequency();
	
	public File getDocumentsSourceFile();

	public File getCorpusSourceFile();
	
	public File getCorpusFile();

	public int getEpochs();
	
	public int getWindowSize();
	
	public int getLayerSize();
	
	// Methods
	
	public void writeToFile(File file) throws IOException;

}
