package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;

class ConfigWord2Vec extends ConfigImpl {
	
	private int				epochs;	
	private int				layerSize;	
	private int				windowSize;	
	
	private String			corpusPath;	
	
	ConfigWord2Vec() {
		super(ConfigType.WORD2VEC);
	}
	
	/*
	 * Getters & Setters
	 */

	@Override
	public int getEpochs() {
		return epochs;
	}

	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}

	@Override
	public int getLayerSize() {
		return layerSize;
	}

	public void setLayerSize(int layerSize) {
		this.layerSize = layerSize;
	}

	@Override
	public int getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
	
	@Override
	public File getCorpusFile() {
		if(corpusPath!=null) {
			return new File(corpusPath);
		}
		return null;
	}

	public void setCorpusFile(File corpusFile) {
		if(corpusFile!=null) {
			this.corpusPath = corpusFile.getPath();
		} else {
			this.corpusPath = null;
		}
	}
		
}
