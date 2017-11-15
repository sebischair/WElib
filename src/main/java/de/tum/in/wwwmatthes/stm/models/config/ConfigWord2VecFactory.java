package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.util.List;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;

public class ConfigWord2VecFactory extends ConfigFactory {
	
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
	
	public ConfigWord2VecFactory() {
		super(ConfigType.WORD2VEC);
	}
	
	// Public Methods - Override
	
	@Override
	public ConfigWord2VecFactory useIdentifier(String identifier) {
		return  (ConfigWord2VecFactory) super.useIdentifier(identifier);
	}
	
	@Override
	public ConfigWord2VecFactory allowedPosTags(List<String> allowedPosTags) {
		return (ConfigWord2VecFactory) super.allowedPosTags(allowedPosTags);
	}
	
	@Override
	public ConfigWord2VecFactory enableStemming(boolean enableStemming) {
		return (ConfigWord2VecFactory) super.enableStemming(enableStemming);
	}
	
	@Override
	public ConfigWord2VecFactory enablePreprocessing(boolean enableStemming) {
		return (ConfigWord2VecFactory) super.enableStemming(enableStemming);
	}
	
	@Override
	public ConfigWord2VecFactory addDefaultStopWords(boolean addDefaultStopWords) {
		return (ConfigWord2VecFactory) super.addDefaultStopWords(addDefaultStopWords);
	}
	
	@Override
	public ConfigWord2VecFactory minWordFrequency(int minWordFrequency) {
		return (ConfigWord2VecFactory) super.minWordFrequency(minWordFrequency);
	}
	
	@Override
	public ConfigWord2VecFactory documentsSourceFile(File documentsSourceFile) {
		return (ConfigWord2VecFactory) super.documentsSourceFile(documentsSourceFile);
	}
	
	@Override
	public ConfigWord2VecFactory stopWords(List<String> stopWords) {
		return (ConfigWord2VecFactory) super.stopWords(stopWords);
	}
		
	// Public Methods
	
	public ConfigWord2VecFactory corpusFile(File corpusFile) {
		this.corpusFile = corpusFile;
		return this;
	}
	
	public ConfigWord2VecFactory windowSize(int windowSize) {
		this.windowSize = windowSize;
		return this;
	}
	
	public ConfigWord2VecFactory layerSize(int layerSize) {
		this.layerSize = layerSize;
		return this;
	}
	
	public ConfigWord2VecFactory batchSize(int batchSize) {
		this.batchSize = batchSize;
		return this;
	}
	
	public ConfigWord2VecFactory epochs(int epochs) {
		this.epochs = epochs;
		return this;
	}
	
	public ConfigWord2VecFactory iterations(int iterations) {
		this.iterations = iterations;
		return this;
	}
	
	public ConfigWord2VecFactory minLearningRate(double minLearningRate) {
		this.minLearningRate = minLearningRate;
		return this;
	}
	
	public ConfigWord2VecFactory learningRate(double learningRate) {
		this.learningRate = learningRate;
		return this;
	}
	
	public ConfigWord2VecFactory sampling(double sampling) {
		this.sampling = sampling;
		return this;
	}
	
	public ConfigWord2VecFactory negativeSample(double negativeSample) {
		this.negativeSample = negativeSample;
		return this;
	}
	
	@Override
	public Config build() throws InvalidConfigException {
		
		if (corpusFile == null) {
			throw new InvalidConfigException();
		}
		
		ConfigWord2Vec configWORD2VEC = new ConfigWord2Vec();
		configWORD2VEC.setIdentifier(identifier);
		configWORD2VEC.setMinWordFrequency(minWordFrequency); 
		configWORD2VEC.setStemmingEnabled(stemmingEnabled);
		configWORD2VEC.setPreprocessingEnabled(preprocessingEnabled);
		configWORD2VEC.setAllowedPosTags(allowedPosTags);
		configWORD2VEC.setStopWords(stopWords);
		configWORD2VEC.setDocumentsSourceFile(documentsSourceFile);
		configWORD2VEC.setAddDefaultStopWords(addDefaultStopWords);
		
		configWORD2VEC.setEpochs(epochs);	
		configWORD2VEC.setLayerSize(layerSize);
		configWORD2VEC.setWindowSize(windowSize);
		configWORD2VEC.setBatchSize(batchSize);
		configWORD2VEC.setCorpusFile(corpusFile);
		configWORD2VEC.setMinLearningRate(minLearningRate);	
		configWORD2VEC.setLearningRate(learningRate);	
		configWORD2VEC.setSampling(sampling); 
		configWORD2VEC.setNegativeSample(negativeSample); 
		configWORD2VEC.setIterations(iterations);
		
		return configWORD2VEC;
	}

}
