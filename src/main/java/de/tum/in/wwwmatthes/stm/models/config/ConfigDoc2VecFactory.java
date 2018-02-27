package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.util.List;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;

public class ConfigDoc2VecFactory extends ConfigFactory {
	
	private Integer		iterations		= 1;
	private Integer		epochs			= 1;	
	private Integer		layerSize		= 100;	
	private Integer		windowSize		= 5;
	private Integer		batchSize		= 128;	
	
	private Double 		learningRate		= 0.025;
	private Double 		minLearningRate	= 0.0001;
	
	private Double 		sampling			= 0.0;		
	private Double 		negativeSample 	= 0.0;
	
	private File			corpusFile;	
	private File			corpusSourceFile;	
	
	public ConfigDoc2VecFactory() {
		super(ConfigType.DOC2VEC);
	}
	
	// Public Methods - Override
	
	@Override
	public ConfigDoc2VecFactory useIdentifier(String identifier) {
		return  (ConfigDoc2VecFactory) super.useIdentifier(identifier);
	}
	
	@Override
	public ConfigDoc2VecFactory allowedPosTags(List<String> allowedPosTags) {
		return (ConfigDoc2VecFactory) super.allowedPosTags(allowedPosTags);
	}
	
	@Override
	public ConfigDoc2VecFactory enableStemming(Boolean enableStemming) {
		return (ConfigDoc2VecFactory) super.enableStemming(enableStemming);
	}
	
	@Override
	public ConfigDoc2VecFactory enablePreprocessing(Boolean preprocessingEnabled) {
		return (ConfigDoc2VecFactory) super.enablePreprocessing(preprocessingEnabled);
	}
	
	
	@Override
	public ConfigDoc2VecFactory addDefaultStopWords(Boolean addDefaultStopWords) {
		return (ConfigDoc2VecFactory) super.addDefaultStopWords(addDefaultStopWords);
	}
	
	@Override
	public ConfigDoc2VecFactory minWordFrequency(Integer minWordFrequency) {
		return (ConfigDoc2VecFactory) super.minWordFrequency(minWordFrequency);
	}
	
	@Override
	public ConfigDoc2VecFactory stopWords(List<String> stopWords) {
		return (ConfigDoc2VecFactory) super.stopWords(stopWords);
	}
		
	// Public Methods
	
	public ConfigDoc2VecFactory corpusFile(File corpusFile) {
		this.corpusFile = corpusFile;
		return this;
	}
	
	public ConfigDoc2VecFactory corpusSourceFile(File corpusSourceFile) {
		this.corpusSourceFile = corpusSourceFile;
		return this;
	}
	
	public ConfigDoc2VecFactory windowSize(Integer windowSize) {
		this.windowSize = windowSize;
		return this;
	}
	
	public ConfigDoc2VecFactory layerSize(Integer layerSize) {
		this.layerSize = layerSize;
		return this;
	}
	
	public ConfigDoc2VecFactory batchSize(Integer batchSize) {
		this.batchSize = batchSize;
		return this;
	}
	
	public ConfigDoc2VecFactory epochs(Integer epochs) {
		this.epochs = epochs;
		return this;
	}
	
	public ConfigDoc2VecFactory iterations(Integer iterations) {
		this.iterations = iterations;
		return this;
	}
	
	public ConfigDoc2VecFactory minLearningRate(Double minLearningRate) {
		this.minLearningRate = minLearningRate;
		return this;
	}
	
	public ConfigDoc2VecFactory learningRate(Double learningRate) {
		this.learningRate = learningRate;
		return this;
	}
	
	public ConfigDoc2VecFactory sampling(Double sampling) {
		this.sampling = sampling;
		return this;
	}
	
	public ConfigDoc2VecFactory negativeSample(Double negativeSample) {
		this.negativeSample = negativeSample;
		return this;
	}
	
	@Override
	public Config build() throws InvalidConfigException {
		
		if (corpusFile == null && corpusSourceFile == null) {
			throw new InvalidConfigException();
		}
		
		ConfigDoc2Vec configDOC2VEC = new ConfigDoc2Vec();
		
		configDOC2VEC.setIdentifier(identifier);
		configDOC2VEC.setMinWordFrequency(minWordFrequency); 
		configDOC2VEC.setStemmingEnabled(stemmingEnabled);
		configDOC2VEC.setPreprocessingEnabled(preprocessingEnabled);
		configDOC2VEC.setAllowedPosTags(allowedPosTags);
		configDOC2VEC.setStopWords(stopWords);
		configDOC2VEC.setAddDefaultStopWords(addDefaultStopWords);
		
		configDOC2VEC.setEpochs(epochs);	
		configDOC2VEC.setIterations(iterations);
		configDOC2VEC.setLayerSize(layerSize);
		configDOC2VEC.setWindowSize(windowSize);
		configDOC2VEC.setBatchSize(batchSize);
		configDOC2VEC.setCorpusFile(corpusFile);
		configDOC2VEC.setCorpusSourceFile(corpusSourceFile);
		configDOC2VEC.setMinLearningRate(minLearningRate);	
		configDOC2VEC.setLearningRate(learningRate);	
		configDOC2VEC.setSampling(sampling); 
		configDOC2VEC.setNegativeSample(negativeSample); 
		
		return configDOC2VEC;
	}

}
