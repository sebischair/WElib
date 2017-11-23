package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;

class ConfigDoc2Vec extends ConfigImpl {
	
	private Integer		iterations;
	private Integer		epochs;	
	private Integer		layerSize;	
	private Integer		windowSize;
	private Integer		batchSize;	
	
	private Double 		learningRate;
	private Double 		minLearningRate;
	
	private Double 		sampling;		
	private Double 		negativeSample;
	
	private String		corpusPath;	
	private String		corpusSourcePath;
	
	ConfigDoc2Vec() {
		super(ConfigType.DOC2VEC);
	}
	
	/*
	 * Getters & Setters
	 */

	@Override
	public Integer getEpochs() {
		return epochs;
	}

	public void setEpochs(Integer epochs) {
		this.epochs = epochs;
	}

	@Override
	public Integer getLayerSize() {
		return layerSize;
	}

	public void setLayerSize(Integer layerSize) {
		this.layerSize = layerSize;
	}

	@Override
	public Integer getWindowSize() {
		return windowSize;
	}

	public void setWindowSize(Integer windowSize) {
		this.windowSize = windowSize;
	}

	@Override
	public Integer getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}
	
	@Override
	public Double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(Double learningRate) {
		this.learningRate = learningRate;
	}

	@Override
	public Double getMinLearningRate() {
		return minLearningRate;
	}

	public void setMinLearningRate(Double minLearningRate) {
		this.minLearningRate = minLearningRate;
	}

	@Override
	public Double getSampling() {
		return sampling;
	}

	public void setSampling(Double sampling) {
		this.sampling = sampling;
	}

	@Override
	public Double getNegativeSample() {
		return negativeSample;
	}

	public void setNegativeSample(Double negativeSample) {
		this.negativeSample = negativeSample;
	}
	
	@Override
	public Integer getIterations() {
		return iterations;
	}

	public void setIterations(Integer iterations) {
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
	public File getCorpusSourceFile() {
		if(corpusSourcePath!=null) {
			return new File(corpusSourcePath);
		}
		return null;
	}
	
	public void setCorpusSourceFile(File corpusSourceFile) {
		if(corpusSourceFile!=null) {
			this.corpusSourcePath = corpusSourceFile.getPath();
		} else {
			this.corpusSourcePath = null;
		}
	}

	@Override
	public String toString() {
		return "ConfigDoc2Vec [iterations=" + iterations + ", epochs=" + epochs + ", layerSize=" + layerSize
				+ ", windowSize=" + windowSize + ", batchSize=" + batchSize + ", learningRate=" + learningRate
				+ ", minLearningRate=" + minLearningRate + ", sampling=" + sampling + ", negativeSample="
				+ negativeSample + ", corpusPath=" + corpusPath + ", toString()=" + super.toString() + "]";
	}


	
}
