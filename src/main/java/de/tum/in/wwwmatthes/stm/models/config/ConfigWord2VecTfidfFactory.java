package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.util.List;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;

public class ConfigWord2VecTfidfFactory extends ConfigFactory {

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

	public ConfigWord2VecTfidfFactory() {
		super(ConfigType.WORD2VEC_TFIDF);
	}
	
	// Public Methods - Override
	
	@Override
	public ConfigWord2VecTfidfFactory useIdentifier(String identifier) {
		return  (ConfigWord2VecTfidfFactory) super.useIdentifier(identifier);
	}
	
	@Override
	public ConfigWord2VecTfidfFactory allowedPosTags(List<String> allowedPosTags) {
		return (ConfigWord2VecTfidfFactory) super.allowedPosTags(allowedPosTags);
	}
	
	@Override
	public ConfigWord2VecTfidfFactory enableStemming(Boolean enableStemming) {
		return (ConfigWord2VecTfidfFactory) super.enableStemming(enableStemming);
	}
	
	@Override
	public ConfigWord2VecTfidfFactory enablePreprocessing(Boolean enableStemming) {
		return (ConfigWord2VecTfidfFactory) super.enablePreprocessing(enableStemming);
	}
	
	@Override
	public ConfigWord2VecTfidfFactory addDefaultStopWords(Boolean addDefaultStopWords) {
		return (ConfigWord2VecTfidfFactory) super.addDefaultStopWords(addDefaultStopWords);
	}
	
	@Override
	public ConfigWord2VecTfidfFactory minWordFrequency(Integer minWordFrequency) {
		return (ConfigWord2VecTfidfFactory) super.minWordFrequency(minWordFrequency);
	}
	
	@Override
	public ConfigWord2VecTfidfFactory documentsSourceFile(File documentsSourceFile) {
		return (ConfigWord2VecTfidfFactory) super.documentsSourceFile(documentsSourceFile);
	}
	
	@Override
	public ConfigWord2VecTfidfFactory stopWords(List<String> stopWords) {
		return (ConfigWord2VecTfidfFactory) super.stopWords(stopWords);
	}
		
	// Public Methods
	
	public ConfigWord2VecTfidfFactory corpusFile(File corpusFile) {
		this.corpusFile = corpusFile;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory corpusSourceFile(File corpusSourceFile) {
		this.corpusSourceFile = corpusSourceFile;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory windowSize(Integer windowSize) {
		this.windowSize = windowSize;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory layerSize(Integer layerSize) {
		this.layerSize = layerSize;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory batchSize(Integer batchSize) {
		this.batchSize = batchSize;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory epochs(Integer epochs) {
		this.epochs = epochs;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory iterations(Integer iterations) {
		this.iterations = iterations;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory minLearningRate(Double minLearningRate) {
		this.minLearningRate = minLearningRate;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory learningRate(Double learningRate) {
		this.learningRate = learningRate;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory sampling(Double sampling) {
		this.sampling = sampling;
		return this;
	}
	
	public ConfigWord2VecTfidfFactory negativeSample(Double negativeSample) {
		this.negativeSample = negativeSample;
		return this;
	}
	
	@Override
	public Config build() throws InvalidConfigException {
		
		if (corpusFile == null && corpusSourceFile == null) {
			throw new InvalidConfigException();
		}
		
		ConfigWord2Vec configWORD2VEC = new ConfigWord2VecTfidf();
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
		configWORD2VEC.setCorpusSourceFile(corpusSourceFile);
		configWORD2VEC.setMinLearningRate(minLearningRate);	
		configWORD2VEC.setLearningRate(learningRate);	
		configWORD2VEC.setSampling(sampling); 
		configWORD2VEC.setNegativeSample(negativeSample); 
		configWORD2VEC.setIterations(iterations);
		
		return configWORD2VEC;
	}
	
}
