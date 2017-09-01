package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import de.tum.in.wwwmatthes.stm.exceptions.InvalidConfigException;
import de.tum.in.wwwmatthes.stm.preprocessing.StopWords;
import de.tum.in.wwwmatthes.stm.util.FileUtil;

public class ConfigFactory {
	
	private String 		key;
	private ConfigType	type;
	
	private List<String>	stopWords				= new ArrayList<String>();
	private boolean		useDefaultStopWords		= true;			
	private int 			minWordFrequency			= 0;
	
	private File 		documentsSourceFile		= null;
		
	// Word2Vec & Doc2Vec
	private int	layerSize 	= 0;
	private int	windowSize 	= 0;
	private int	epochs 		= 0;
	
	private File corpusSourceFile = null;
	
	// Config File
	private File 		configFile;
	
	public ConfigFactory useConfigFile(File configFile) {
		this.configFile = configFile;
		return this;
	}
	
	public ConfigFactory key(String key) {
		this.key = key;
		return this;
	}
	
	public ConfigFactory type(ConfigType type) {
		this.type = type;
		return this;
	}
	
	public ConfigFactory useDefaultStopWords(boolean useDefaultStopWords) {
		this.useDefaultStopWords = useDefaultStopWords;
		return this;
	}
	
	public ConfigFactory minWordFrequency(int minWordFrequency) {
		this.minWordFrequency = minWordFrequency;
		return this;
	}
	
	public ConfigFactory documentsSourceFile(File documentsSourceFile) {
		this.documentsSourceFile = documentsSourceFile;
		return this;
	}
	
	public ConfigFactory corpusSourceFile(File corpusSourceFile) {
		this.corpusSourceFile = corpusSourceFile;
		return this;
	}
	
	public ConfigFactory windowSize(int windowSize) {
		this.windowSize = windowSize;
		return this;
	}
	
	public ConfigFactory layerSize(int layerSize) {
		this.layerSize = layerSize;
		return this;
	}
	
	public ConfigFactory epochs(int epochs) {
		this.epochs = epochs;
		return this;
	}

	public Config build() throws InvalidConfigException {
		
		// If Config File exists
		if (configFile != null) {
			if (configFile.exists()) {
				importJSONFile(configFile);
			} else {
				// Print: 
			}
		}
		
		// Check Type & Key
		
		if (type == null) {
			throw new InvalidConfigException();
		} else if (key == null) {
			throw new InvalidConfigException();
		}
		
		switch (type) {
		
		case TFIDF:
			ConfigTFIDF configTFIDF = new ConfigTFIDF(key);
			configTFIDF.setMinWordFrequency(minWordFrequency);
			configTFIDF.setStopWords(stopWords);
			configTFIDF.setDocumentsSourceFile(documentsSourceFile);
			configTFIDF.setUseDefaultStopWords(useDefaultStopWords);
			
			return configTFIDF;
			
		case WORD2VEC:
			ConfigTFIDF configWORD2VEC = new ConfigTFIDF(key);
			configWORD2VEC.setMinWordFrequency(minWordFrequency);
			configWORD2VEC.setStopWords(stopWords);
			configWORD2VEC.setDocumentsSourceFile(documentsSourceFile);
			configWORD2VEC.setUseDefaultStopWords(useDefaultStopWords);
			configWORD2VEC.setEpochs(epochs);
			
			return configWORD2VEC;
			
		case DOC2VEC:
			ConfigTFIDF configDOC2VEC = new ConfigTFIDF(key);
			configDOC2VEC.setMinWordFrequency(minWordFrequency);
			configDOC2VEC.setStopWords(stopWords);
			configDOC2VEC.setDocumentsSourceFile(documentsSourceFile);
			configDOC2VEC.setUseDefaultStopWords(useDefaultStopWords);
			configDOC2VEC.setEpochs(epochs);
			
			return configDOC2VEC;

		default:
			throw new InvalidConfigException();
		}
	}
	
	/*
	 * JSON-Import
	 */
	
	private static final String JSON_KEY_TYPE 				= "type";
	private static final String JSON_KEY_STOPWORDS 			= "stopWords";
	private static final String JSON_KEY_ADDDEFAULTSTOPWORDS 	= "addDefaultStopWords";
	private static final String JSON_KEY_MINWORDFREQUENCY 	= "minWordFrequency";
	private static final String JSON_KEY_DOCUMENTSSOURCEFILE 	= "documentsSourceFile";
	private static final String JSON_KEY_EPOCHS 				= "epochs";
	private static final String JSON_KEY_LAYERSIZE 			= "layerSize";
	private static final String JSON_KEY_WINDOWSIZE 			= "windowSize";
	private static final String JSON_KEY_CORPUSSOURCEFILE 	= "corpusSourceFile";
	
	private void importJSONFile(File file) {
		
		// Load File
		String string = null;
		try {
			string = FileUtil.readFile(file);
		} catch (IOException e) {
			// Ignore & return
			return;
		}
		
		// Load File Into JSONObject
		JSONObject object = new JSONObject(string).getJSONObject("config");
		
		//// Write Values
		
		this.key		= FileUtil.getFileNameWithoutExtension(file);
		this.type	= ConfigType.typeFromString(object.getString(JSON_KEY_TYPE));
		
		// Stop Words	
		if (object.has(JSON_KEY_STOPWORDS)) {
			this.stopWords.addAll(jsonArrayToStringList(object.getJSONArray(JSON_KEY_STOPWORDS)));
		}
		
		// Min Word Frequency
		if (object.has(JSON_KEY_MINWORDFREQUENCY)) {
			this.minWordFrequency = object.getInt(JSON_KEY_MINWORDFREQUENCY);
		} else {
			this.minWordFrequency = 0;
		}
		
		// Documents Source File	
		if (object.has(JSON_KEY_DOCUMENTSSOURCEFILE)) {
			File documentsSourceFile = new File(object.getString(JSON_KEY_DOCUMENTSSOURCEFILE));
			if (documentsSourceFile.exists()) {
				this.documentsSourceFile = documentsSourceFile;
			} else {
				this.documentsSourceFile = null;
			}
		} else {
			this.documentsSourceFile = null;
		}
		
		// Documents Source File	
		if (object.has(JSON_KEY_CORPUSSOURCEFILE)) {
			File documentsSourceFile = new File(object.getString(JSON_KEY_CORPUSSOURCEFILE));
			if (documentsSourceFile.exists()) {
				this.corpusSourceFile = documentsSourceFile;
			} else {
				this.corpusSourceFile = null;
			}
		} else {
			this.corpusSourceFile = null;
		}
		
		// Add Default Stop Words		
		if (object.getBoolean(JSON_KEY_ADDDEFAULTSTOPWORDS)) {
			this.stopWords.addAll(StopWords.getStopWords());
		}
		
		// Epochs
		if (object.has(JSON_KEY_EPOCHS)) {
			this.epochs = object.getInt(JSON_KEY_EPOCHS);
		} else {
			this.epochs = 0;
		}
		
		// Window Size
		if (object.has(JSON_KEY_WINDOWSIZE)) {
			this.windowSize = object.getInt(JSON_KEY_WINDOWSIZE);
		} else {
			this.windowSize = 0;
		}
		
		// Layer Size
		if (object.has(JSON_KEY_LAYERSIZE)) {
			this.layerSize = object.getInt(JSON_KEY_LAYERSIZE);
		} else {
			this.layerSize = 0;
		}
	}
	
	/*
	 * Private Helper Methods
	 */
	
	private List<String> jsonArrayToStringList(JSONArray array) {
		List<String> list = new ArrayList<String>();     
		for (int i = 0; i < array.length(); i++){ 
		    list.add(array.get(i).toString());
		} 
		return list;
	}
	
	
}
