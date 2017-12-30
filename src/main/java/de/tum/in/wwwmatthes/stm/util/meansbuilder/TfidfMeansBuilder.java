package de.tum.in.wwwmatthes.stm.util.meansbuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.deeplearning4j.bagofwords.vectorizer.TfidfVectorizer;
import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.deeplearning4j.util.MathUtils;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class TfidfMeansBuilder extends MeansBuilder {

	private TfidfVectorizer tfidfVectorizer;
	private Map<String, Double> idfLookupTable;
	private Double defaultIdf;
	
    public TfidfMeansBuilder(WeightLookupTable<VocabWord> lookupTable, TokenizerFactory tokenizerFactory, TfidfVectorizer tfidfVectorizer) {
		super(lookupTable, tokenizerFactory);
		this.tfidfVectorizer	= tfidfVectorizer;
		this.defaultIdf 		= TfidfMeansBuilder.getMaxIdfFromLookupTable(tfidfVectorizer);
	}

	@Override
	protected INDArray transform(VocabWord token, List<VocabWord> tokens) {
		
		// Word Count
		AtomicLong count = new AtomicLong(0);
        for (VocabWord thisToken : tokens) {
        		if (token.getWord().equals(thisToken.getWord())) {
        			count.incrementAndGet();
        		}
        }
		
        // TFIDF
		Double tfidf = null;
		if(count.get()>0) {
			tfidf = tfidfVectorizer.tfidfWord(token.getWord(), count.get(), tokens.size());
		} else {
			tfidf = tfForWord(count.get(), tokens.size()) * defaultIdf;
		}
		
		// Result
		return lookupTable.vector(token.getWord()).mul(tfidf);
	}
	
	// MARK: - TF-IDF Helper Methods
	
	private static double getMaxIdfFromLookupTable(TfidfVectorizer tfidfVectorizer) {
		Map<String, Double> table = createIdfLookupTable(tfidfVectorizer);
		
		double maxIdf = Double.MIN_VALUE;
		for(String word : table.keySet()) {
			double value = table.get(word);
			if (value > maxIdf) {
				maxIdf = value;
			}
		}
		
		return maxIdf;
	}
	
	private static double idfForWord(String word, VocabCache<VocabWord> vocabCache) {
	    return MathUtils.idf(vocabCache.totalNumberOfDocs(), vocabCache.docAppearedIn(word));
	}
	
	private static double tfForWord(long wordCount, long documentLength) {
	    return (double) wordCount / (double) documentLength;
	}
	
	private static Map<String, Double> createIdfLookupTable(TfidfVectorizer tfidfVectorizer) {
			VocabCache<VocabWord> cache = tfidfVectorizer.getVocabCache();
			Map<String, Double> table = new HashMap<String, Double>();
			for(VocabWord vocabWord : cache.tokens()) {
				table.put(vocabWord.getWord(), idfForWord(vocabWord.getWord(), cache));
			}
			return table;
	}

}
