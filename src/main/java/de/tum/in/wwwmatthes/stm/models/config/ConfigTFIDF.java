package de.tum.in.wwwmatthes.stm.models.config;

import java.io.File;
import java.io.IOException;

import org.json.JSONObject;

class ConfigTFIDF extends ConfigImpl {
	
	ConfigTFIDF(String key) {
		super(key, ConfigType.TFIDF);
	}

}
