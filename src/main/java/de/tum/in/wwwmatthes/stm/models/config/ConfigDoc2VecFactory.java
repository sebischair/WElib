package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.util.List;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;

public class ConfigDoc2VecFactory extends ConfigFactory {
	
	private int			iterations		= 1;
	private int			epochs			= 1;	
	private int			layerSize		= 100;	
	private int			windowSize		= 5;
	private int			batchSize		= 128;	
	
	private double 		learningRate		= 0.025;
	private double 		minLearningRate	= 0.0001;
	
	private double 		sampling			= 0;		
	private double 		negativeSample 	= 0;
	
	private File			corpusFile;	
	
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
	public ConfigDoc2VecFactory enableStemming(boolean enableStemming) {
		return (ConfigDoc2VecFactory) super.enableStemming(enableStemming);
	}
	
	@Override
	public ConfigDoc2VecFactory enablePreprocessing(boolean enableStemming) {
		return (ConfigDoc2VecFactory) super.enableStemming(enableStemming);
	}
	
	
	@Override
	public ConfigDoc2VecFactory addDefaultStopWords(boolean addDefaultStopWords) {
		return (ConfigDoc2VecFactory) super.addDefaultStopWords(addDefaultStopWords);
	}
	
	@Override
	public ConfigDoc2VecFactory minWordFrequency(int minWordFrequency) {
		return (ConfigDoc2VecFactory) super.minWordFrequency(minWordFrequency);
	}
	
	@Override
	public ConfigDoc2VecFactory documentsSourceFile(File documentsSourceFile) {
		return (ConfigDoc2VecFactory) super.documentsSourceFile(documentsSourceFile);
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
	
	public ConfigDoc2VecFactory windowSize(int windowSize) {
		this.windowSize = windowSize;
		return this;
	}
	
	public ConfigDoc2VecFactory layerSize(int layerSize) {
		this.layerSize = layerSize;
		return this;
	}
	
	public ConfigDoc2VecFactory batchSize(int batchSize) {
		this.batchSize = batchSize;
		return this;
	}
	
	public ConfigDoc2VecFactory epochs(int epochs) {
		this.epochs = epochs;
		return this;
	}
	
	public ConfigDoc2VecFactory iterations(int iterations) {
		this.iterations = iterations;
		return this;
	}
	
	public ConfigDoc2VecFactory minLearningRate(double minLearningRate) {
		this.minLearningRate = minLearningRate;
		return this;
	}
	
	public ConfigDoc2VecFactory learningRate(double learningRate) {
		this.learningRate = learningRate;
		return this;
	}
	
	public ConfigDoc2VecFactory sampling(double sampling) {
		this.sampling = sampling;
		return this;
	}
	
	public ConfigDoc2VecFactory negativeSample(double negativeSample) {
		this.negativeSample = negativeSample;
		return this;
	}
	
	@Override
	public Config build() throws InvalidConfigException {
		
		if (corpusFile == null) {
			throw new InvalidConfigException();
		}
		
		ConfigDoc2Vec configDOC2VEC = new ConfigDoc2Vec();
		configDOC2VEC.setIdentifier(identifier);
		configDOC2VEC.setMinWordFrequency(minWordFrequency); 
		configDOC2VEC.setStemmingEnabled(stemmingEnabled);
		configDOC2VEC.setPreprocessingEnabled(preprocessingEnabled);
		configDOC2VEC.setAllowedPosTags(allowedPosTags);
		configDOC2VEC.setStopWords(stopWords);
		configDOC2VEC.setDocumentsSourceFile(documentsSourceFile);
		configDOC2VEC.setAddDefaultStopWords(addDefaultStopWords);
		
		configDOC2VEC.setEpochs(epochs);	
		configDOC2VEC.setLayerSize(layerSize);
		configDOC2VEC.setWindowSize(windowSize);
		configDOC2VEC.setBatchSize(batchSize);
		configDOC2VEC.setCorpusFile(corpusFile);
		configDOC2VEC.setMinLearningRate(minLearningRate);	
		configDOC2VEC.setLearningRate(learningRate);	
		configDOC2VEC.setSampling(sampling); 
		configDOC2VEC.setNegativeSample(negativeSample); 
		configDOC2VEC.setIterations(iterations);
		
		return configDOC2VEC;
	}

}
