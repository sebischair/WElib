package de.tum.in.wwwmatthes.stm.preprocessing;

import java.util.regex.Pattern;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;

public class PreProcessor extends CommonPreprocessor {
	
	private static final Pattern punctPattern = Pattern.compile("[$%]+");
	
	@Override
	public String preProcess(String token) {
		String superString = super.preProcess(token);
		return superString; //PreProcessor.clean(superString);
	}

    private static String clean(String base) {
        return punctPattern.matcher(base).replaceAll("");
    }
	
}
