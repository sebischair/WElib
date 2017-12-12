package de.tum.in.wwwmatthes.stm.tokenizers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.util.JCasUtil;
import org.cleartk.token.type.Sentence;
import org.cleartk.token.type.Token;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;

import lombok.NonNull;

class CustomTokenizer implements Tokenizer {

    private List<String> tokens;
    private Collection<String> allowedPosTags;
    private int index;
    private TokenPreProcess preProcessor;
    private boolean stripNones = false;

    CustomTokenizer(String tokens, AnalysisEngine engine, CAS cas, Collection<String> allowedPosTags) {
        this(tokens, engine, cas, allowedPosTags, false);
    }

    CustomTokenizer(String tokens, AnalysisEngine en, CAS cas, Collection<String> allowedPosTags,
                    boolean stripNones) {
        this.allowedPosTags = allowedPosTags;
        this.tokens = new ArrayList<String>();
        this.stripNones = stripNones;
        try {
            cas.reset();
            cas.setDocumentText(tokens);
            en.process(cas);
            for (Sentence s : JCasUtil.select(cas.getJCas(), Sentence.class)) {
                for (Token t : JCasUtil.selectCovered(Token.class, s)) {
                    //add NONE for each invalid token
                    if (valid(t))
                        if (t.getLemma() != null)
                            this.tokens.add(t.getLemma());
                        else if (t.getStem() != null)
                            this.tokens.add(t.getStem());
                        else
                            this.tokens.add(t.getCoveredText());
                    else
                        this.tokens.add("NONE");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean valid(Token token) {
        String check = token.getCoveredText();
        if (check.matches("<[A-Z]+>") || check.matches("</[A-Z]+>")
                        || (token.getPos() != null && !this.allowedPosTags.contains(token.getPos())))
            return false;
        return true;
    }
    
    @Override
    public boolean hasMoreTokens() {
        return index < tokens.size();
    }

    @Override
    public int countTokens() {
        return tokens.size();
    }

    @Override
    public String nextToken() {
        String ret = tokens.get(index); // preProcessor != null ? preProcessor.preProcess(tokens.get(index)) : tokens.get(index);
        index++;
        return ret;
    }

    @Override
    public List<String> getTokens() {
        List<String> tokens = new ArrayList<String>();
        while (hasMoreTokens()) {
            String nextT = nextToken();
            if (stripNones && nextT.equals("NONE"))
                continue;
            tokens.add(preProcessor != null ? preProcessor.preProcess(nextT) : nextT);
        }
        return tokens;
    }

    @Override
    public void setTokenPreProcessor(@NonNull TokenPreProcess tokenPreProcessor) {
        this.preProcessor = tokenPreProcessor;
    }

}
