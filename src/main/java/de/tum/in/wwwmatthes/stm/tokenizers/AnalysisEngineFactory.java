package de.tum.in.wwwmatthes.stm.tokenizers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.impl.MultiprocessingAnalysisEngine_impl;
import org.apache.uima.resource.Resource;
import org.deeplearning4j.text.annotator.PoStagger;
import org.deeplearning4j.text.annotator.SentenceAnnotator;
import org.deeplearning4j.text.annotator.StemmerAnnotator;
import org.deeplearning4j.text.annotator.TokenizerAnnotator;

class AnalysisEngineFactory {
	
	static AnalysisEngine createAnalysisEngine(boolean stemmingEnabled, Collection<String> allowedPosTags) {
			
		try {
			AnalysisEngineDescription sentenceDescr 	= SentenceAnnotator.getDescription();
			AnalysisEngineDescription annotatorDescr	= TokenizerAnnotator.getDescription();
			AnalysisEngineDescription posTaggerDescr	= PoStagger.getDescription("en");
			AnalysisEngineDescription stemmerDescr 	= StemmerAnnotator.getDescription("English");
			
			if (stemmingEnabled && allowedPosTags != null) {
				AnalysisEngineDescription descr	= org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr, posTaggerDescr, stemmerDescr);
				return org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine(descr);
			
			} else if (allowedPosTags != null) {
				AnalysisEngineDescription descr	= org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr, posTaggerDescr);
				return org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine(descr);
			
			} else if (stemmingEnabled) {
				AnalysisEngineDescription descr	= org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr, stemmerDescr);
				return org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine(descr);			
			} 
						
			AnalysisEngineDescription descr	= org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription(sentenceDescr, annotatorDescr);
			return org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine(descr);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
