package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.util.List;

public interface Config {
	
	public ConfigType getType();
	
	public List<String> getStopWords();
	
	public boolean isUseDefaultStopWords();

	public int getMinWordFrequency();
	
	public File getDocumentsSourceFile();

	public File getCorpusSourceFile();
	
	public File getCorpusFile();

	public int getEpochs();
	
	public int getWindowSize();
	
	public int getLayerSize();

}
