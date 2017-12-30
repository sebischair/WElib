package de.tum.in.wwwmatthes.stm.util.meansbuilder;

import java.util.List;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;

public class DefaultMeansBuilder extends MeansBuilder {

    public DefaultMeansBuilder(WeightLookupTable<VocabWord> lookupTable, TokenizerFactory tokenizerFactory) {
		super(lookupTable, tokenizerFactory);
	}
    
    @Override
    protected INDArray transform(VocabWord token, List<VocabWord> tokens) {
    		return lookupTable.vector(token.getWord());
    }

}
