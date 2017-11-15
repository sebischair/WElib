package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;

class ConfigWord2Vec extends ConfigImpl {
	
	private int			iterations;
	private int			epochs;	
	private int			layerSize;	
	private int			windowSize;
	private int			batchSize;	
	
	private double 		learningRate;
	private double 		minLearningRate;
	
	private double 		sampling;		
	private double 		negativeSample;
	
	private String		corpusPath;	
	
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
	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
	@Override
	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	@Override
	public double getMinLearningRate() {
		return minLearningRate;
	}

	public void setMinLearningRate(double minLearningRate) {
		this.minLearningRate = minLearningRate;
	}

	@Override
	public double getSampling() {
		return sampling;
	}

	public void setSampling(double sampling) {
		this.sampling = sampling;
	}

	@Override
	public double getNegativeSample() {
		return negativeSample;
	}

	public void setNegativeSample(double negativeSample) {
		this.negativeSample = negativeSample;
	}
	
	@Override
	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
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

	@Override
	public String toString() {
		return "ConfigDoc2Vec [iterations=" + iterations + ", epochs=" + epochs + ", layerSize=" + layerSize
				+ ", windowSize=" + windowSize + ", batchSize=" + batchSize + ", learningRate=" + learningRate
				+ ", minLearningRate=" + minLearningRate + ", sampling=" + sampling + ", negativeSample="
				+ negativeSample + ", corpusPath=" + corpusPath + "]";
	}
		
}
