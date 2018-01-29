/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters.wordsplitter;

import java.util.LinkedList;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordfilters.IFilter;

/**
 *
 * @author marcin
 */
public class SplittingFilter implements IFilter {

    private final DictionarySplitter splitter;

    public SplittingFilter(Dictionary fullDictionary, String prefixes, String suffixes) {
        this.splitter = new DictionarySplitter(fullDictionary, prefixes, suffixes);
    }

    @Override
    public Dictionary filter(Dictionary dict) {
        LinkedList<DictEntry> results = new LinkedList<>();
        for (DictEntry item : dict.items()) {
            results.addAll(splitter.split(item));
        }
        return new Dictionary(results);
    }

}
