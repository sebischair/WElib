package de.tum.in.wwwmatthes.stm.tokenizers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceConfigurationException;
import org.apache.uima.resource.ResourceInitializationException;
import org.deeplearning4j.text.annotator.PoStagger;
import org.deeplearning4j.text.annotator.SentenceAnnotator;
import org.deeplearning4j.text.annotator.StemmerAnnotator;
import org.deeplearning4j.text.annotator.TokenizerAnnotator;
import org.deeplearning4j.text.tokenization.tokenizer.PosUimaTokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.PosUimaTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;

import de.tum.in.wwwmatthes.stm.preprocessing.PreProcessor;

public class CustomTokenizerFactory implements TokenizerFactory {
	
	// Properties
	private boolean 				preprocessingEnabled; 
	private boolean 				stemmingEnabled; 
	private Collection<String> 	allowedPosTags;
	
	// Attributes
    private TokenPreProcess tokenPreProcess;
		
	public CustomTokenizerFactory() {
	}

	@Override
	public Tokenizer create(String toTokenize) {
		CustomTokenizer tokenizer = new CustomTokenizer(toTokenize, getAnalysisEngine(), getCAS(), allowedPosTags, true);
		
		if (tokenPreProcess != null) {
        		tokenizer.setTokenPreProcessor(tokenPreProcess);
		}
		return tokenizer;
	}

	@Override
	public Tokenizer create(InputStream toTokenize) {
		 throw new UnsupportedOperationException();
	}

    @Override
    public void setTokenPreProcessor(TokenPreProcess preProcessor) {
        this.tokenPreProcess = preProcessor;
    }
    
    /**
     * Returns TokenPreProcessor set for this TokenizerFactory instance
     *
     * @return TokenPreProcessor instance, or null if no preprocessor was defined
     */
    @Override
    public TokenPreProcess getTokenPreProcessor() {
        return tokenPreProcess;
    }

    // Getters & Setters
    
    public String processString(String toTokenize) {
    		List<String> tokens = create(toTokenize).getTokens();
    		tokens.removeAll(Arrays.asList(""));
    		return String.join(" ", tokens);
    }
    
	public Collection<String> getAllowedPosTags() {
		return allowedPosTags;
	}

	public boolean isPreprocessingEnabled() {
		return preprocessingEnabled;
	}

	public void setPreprocessingEnabled(boolean preprocessingEnabled) {
		if(preprocessingEnabled) {
			tokenPreProcess = new PreProcessor();
		} else {
			tokenPreProcess = null;
		}
		
		this.preprocessingEnabled = preprocessingEnabled;
	}

	public boolean isStemmingEnabled() {
		return stemmingEnabled;
	}

	public void setStemmingEnabled(boolean stemmingEnabled) {
		this.stemmingEnabled = stemmingEnabled;
	}

	public void setAllowedPosTags(Collection<String> allowedPosTags) {
		this.allowedPosTags = allowedPosTags;
	}
	
	// Analysis Engine
	private boolean analysisNeedsRefresh		= false;
	private AnalysisEngine analysisEngine	= null;
	private CAS cas							= null;
	
	private AnalysisEngine getAnalysisEngine() {
		
		if (analysisEngine == null || analysisNeedsRefresh) {
			analysisEngine = de.tum.in.wwwmatthes.stm.tokenizers.AnalysisEngineFactory.createAnalysisEngine(stemmingEnabled, allowedPosTags);
			try {
				cas = analysisEngine.newCAS();
			} catch (ResourceInitializationException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return analysisEngine;
	}

	private CAS getCAS() {
		return cas;
	}
	
   public static void main(String[] args) {
		// TODO Auto-generated method stub
	   Collection<String> allowedPosTagsEmpty = new ArrayList<String>();
       List<String> tags = new ArrayList<String>(); 
       tags.addAll(Arrays.asList("NN", "NNS", "NNP", "NNPS"));
       //tags.addAll(Arrays.asList("CC", "CD", "DT", "EX", "FW", "IN"));
       //tags.addAll(Arrays.asList("JJ", "JJR", "JJS", "LS", "MD"));
       //tags.addAll(Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ"));
       
  		String string 	= "I played soccer yesterday 50$, I am playing now and I will play tomorrow.";
  		String string2 	= "Munich is a nice city. Cities are bigger than villages.";
  		
  		String string3 	= "All information relating to  shall be considered personally identifiable information.";
  		String string4 	= "Personally identifiable information shall generally be classified as .";
  		String string5 	= "All archived commercial information shall be retained for at least .";
  		String string6 	= "All commercial information shall be retained and archived. Commercial information includes .";
  		String string7 	= "Requesting a purchase shall be segregated from approving a purchase.";
  		String string8 	= "Unauthorised hacking or masking of activity shall be prohibited on  IT.";
  		
  		String string9 = "be communicated within the organization; and";
    	
       for(int i = 0; i<2; i++) {
	   		CustomTokenizerFactory tokenizerFactory = new CustomTokenizerFactory();
	   		tokenizerFactory.setStemmingEnabled(i%2==0);
	   		tokenizerFactory.setPreprocessingEnabled(true);
	   		tokenizerFactory.setAllowedPosTags(Arrays.asList("NN", "NNS"));
	   		
	   		Tokenizer tokenizer = tokenizerFactory.create(string);
	   		List<String> tokens = tokenizer.getTokens();
	   		
	   		System.out.println(tokenizerFactory.processString(string9));
       }
	}
    
}
