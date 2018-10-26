/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.parseTexts;

import java.util.List;
import java.util.stream.Collectors;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.parseTexts.extractors.BruteForceExtractor;
import org.mpasko.parseTexts.extractors.IExtractor;
import org.mpasko.parseTexts.stemmers.AggregateStemmer;

/**
 *
 * @author marcin
 */
public class WordsExtractor {

    private final Dictionary dict;
    private final IExtractor extractor;

    public WordsExtractor(Dictionary fullDict) {
        this.dict = fullDict;
        extractor = new BruteForceExtractor(new AggregateStemmer("", ""), dict)
                .setLimits(1, 4);
    }

    public List<DictEntry> extractFromText(String text) {
        return extractFromSentence(text);
    }

    public List<DictEntry> extractFromSentence(String sentence) {
        return extractor.extract(sentence)
                .stream().map(inflection -> inflection.dictionaryWord)
                .collect(Collectors.toList());
    }
}
