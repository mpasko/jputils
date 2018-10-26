package org.mpasko.parseTexts.stemmers;

import java.util.LinkedList;
import java.util.List;

public class AggregateStemmer implements IStemmer {
    private final String prefixes;
    private final String postfixes;
    private List<IStemmer> stemmers = new LinkedList<>();

    public AggregateStemmer(String prexixes, String postfixes) {
        this.prefixes = prexixes;
        this.postfixes = postfixes;
        stemmers.add(new GrammarStemmer());
        stemmers.add(new InfixStemmer(prefixes, postfixes));
    }

    @Override
    public String stem(String word) {
        String current = word;
        for (IStemmer stemmer : stemmers)
        {
            current = stemmer.stem(current);
        }
        return current;
    }
}
