package de.tum.in.wwwmatthes.stm.tokenizers;

import java.io.InputStream;
import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.deeplearning4j.text.annotator.PoStagger;
import org.deeplearning4j.text.annotator.SentenceAnnotator;
import org.deeplearning4j.text.annotator.StemmerAnnotator;
import org.deeplearning4j.text.annotator.TokenizerAnnotator;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.PosUimaTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.UimaTokenizerFactory;

import de.tum.in.wwwmatthes.stm.preprocessing.PreProcessor;

public class STMTokenizerFactory implements TokenizerFactory {
	
	// Properties
	private boolean 				useStemming; 
	private Collection<String> 	allowedPosTags;
	
	// Attributes
    private TokenPreProcess tokenPreProcess;
    
    // Attributes - Analysis Engine
    private TokenizerFactory tokenizerFactory;
		
	public STMTokenizerFactory() {
		setTokenPreProcessor(new PreProcessor());
	}

	@Override
	public Tokenizer create(String toTokenize) {
		Tokenizer tokenizer = getTokenizerFactory().create(toTokenize);
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
    
	public boolean isUseStemming() {
		return useStemming;
	}

	public void setUseStemming(boolean useStemming) {
		this.useStemming = useStemming;
	}

	public Collection<String> getAllowedPosTags() {
		return allowedPosTags;
	}

	public void setAllowedPosTags(Collection<String> allowedPosTags) {
		this.allowedPosTags = allowedPosTags;
	}
	
	// Private Methods - Generate Tokenizer
	private boolean tokenizerNeedsRefresh = false; 
	
	private TokenizerFactory getTokenizerFactory() {
		
		if (tokenizerFactory != null && !tokenizerNeedsRefresh) {
			return tokenizerFactory;
			
		} else {
		
			try {
				AnalysisEngineDescription sentenceDescr 	= SentenceAnnotator.getDescription();
				AnalysisEngineDescription annotatorDescr	= TokenizerAnnotator.getDescription();
				AnalysisEngineDescription posTaggerDescr	= PoStagger.getDescription("en");
				AnalysisEngineDescription stemmerDescr 	= StemmerAnnotator.getDescription("English");
				
				if (useStemming && allowedPosTags != null) {
					AnalysisEngineDescription descr	= AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr, posTaggerDescr, stemmerDescr);
					AnalysisEngine analysisEngine 	= AnalysisEngineFactory.createEngine(descr);
					
					tokenizerFactory = new PosUimaTokenizerFactory(analysisEngine, allowedPosTags);
				
				} else if (allowedPosTags != null) {
					AnalysisEngineDescription descr	= AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr, posTaggerDescr);
					AnalysisEngine analysisEngine 	= AnalysisEngineFactory.createEngine(descr);
					
					tokenizerFactory = new PosUimaTokenizerFactory(analysisEngine, allowedPosTags);
				
				} else if (useStemming) {
					
					AnalysisEngineDescription descr	= AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr, stemmerDescr);
					AnalysisEngine analysisEngine 	= AnalysisEngineFactory.createEngine(descr);
					
					tokenizerFactory = new UimaTokenizerFactory(analysisEngine);
					
				} else {
					
					AnalysisEngineDescription descr	= AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr);
					AnalysisEngine analysisEngine 	= AnalysisEngineFactory.createEngine(descr);

					tokenizerFactory = new UimaTokenizerFactory(analysisEngine);
				}
			        
				return tokenizerFactory;
				
			} catch (Exception e) { // ResourceInitialization
				System.out.println("Errorrr!!!!!");
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}
  
}

/*
 *     public static void main(String[] args) {
    	
	    	Collection<String> allowedPosTags = new ArrayList<String>();
	    	allowedPosTags.add("JJ");
	    	allowedPosTags.add("NN");
	    	allowedPosTags.add("NNS");
	    	allowedPosTags.add("VB");
	    	allowedPosTags.add("VBD");
	    	allowedPosTags.add("VBG");
	    	allowedPosTags.add("VBN");
	    	allowedPosTags.add("VBP");
	    	allowedPosTags.add("VBZ");
	    	allowedPosTags.add("PRP");
    	
    		STMTokenizerFactory tokenizerFactory = new STMTokenizerFactory();
    		tokenizerFactory.setUseStemming(false);
    		tokenizerFactory.setAllowedPosTags(allowedPosTags);
    		
    		//Tokenizer tokenizer = tokenizerFactory.create("Munich is a nice city. Cities are bigger than villages. ");
    		Tokenizer tokenizer = tokenizerFactory.create("I played yesterday, I am playing now and I will play tomorrow.");
    		List<String> tokens = tokenizer.getTokens();
    		System.out.println(tokens);
    		
	}
 * 
*/
