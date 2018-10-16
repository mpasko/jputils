/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons.analizers;

import java.util.List;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;

/**
 *
 * @author marcin
 */
public class WordsExtractor {

    private final Dictionary dict;
    private final DictionarySplitter splitter;

    public WordsExtractor(Dictionary fullDict) {
        this.dict = fullDict;
        splitter = new DictionarySplitter(dict, "", "").setLimits(1, 4);
    }

    public List<DictEntry> extractFromText(String text) {
        return extractFromSentence(text);
    }

    public List<DictEntry> extractFromSentence(String sentence) {
        return splitter.splitText(sentence);
    }
}
