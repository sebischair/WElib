package de.tum.in.wwwmatthes.stm.util.meansbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.deeplearning4j.util.MathUtils;
import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public abstract class MeansBuilder {
	
	private VocabCache<VocabWord> 			vocabCache;
	protected WeightLookupTable<VocabWord> 	lookupTable;
    private TokenizerFactory 				tokenizerFactory;
    
    public MeansBuilder(WeightLookupTable<VocabWord> lookupTable, TokenizerFactory tokenizerFactory) {
        this.lookupTable	= lookupTable;
        this.vocabCache 	= lookupTable.getVocabCache();
        
        this.tokenizerFactory = tokenizerFactory;
    }
    
    public INDArray transformString(String string) {
    	
		// Create Tokens
		List<String> tokenizedString = tokenizerFactory.create(string).getTokens();
	    
		// Get Word Count
		List<VocabWord> tokens = new ArrayList<VocabWord>();
	    for (String word: tokenizedString) {
	        if (vocabCache.containsWord(word)) tokens.add(vocabCache.tokenFor(word));
	    }
    
	    // Return Transformed Tokens
	    if(tokens.size() > 0) {
	    	
		    // Calculate Vector
	        INDArray allWords = Nd4j.create(tokens.size(), lookupTable.layerSize());
	    	
	        AtomicInteger cnt = new AtomicInteger(0);
	        for (VocabWord word: tokens) {
	    			allWords.putRow(cnt.getAndIncrement(), transform(word, tokens));    
	        }
	        
	        return allWords.mean(0);
	    }
	    return null;
    }
    
    protected abstract INDArray transform(VocabWord token, List<VocabWord> tokens); 

}
