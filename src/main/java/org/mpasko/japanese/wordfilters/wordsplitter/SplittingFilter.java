/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters.wordsplitter;

import java.util.LinkedList;
import java.util.List;

import org.mpasko.commons.DictEntry;
import org.mpasko.parseTexts.splitters.DictionarySplitter;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordfilters.IFilter;
import org.mpasko.parseTexts.splitters.ISplitter;

/**
 *
 * @author marcin
 */
public class SplittingFilter implements IFilter {

    private final ISplitter splitter;

    private SplittingFilter(Dictionary fullDictionary, String prefixes, String suffixes) {
        this.splitter = new DictionarySplitter(fullDictionary, prefixes, suffixes);
    }

    public static SplittingFilter StandardSplittingFilter(Dictionary fullDictionary, String prefixes, String suffixes) {
        return new SplittingFilter(fullDictionary, prefixes, suffixes);
    }

    @Override
    public Dictionary filter(Dictionary dict) {
        return new Dictionary(this.filter(dict.items()));
    }

    @Override
    public List<DictEntry> filter(List<DictEntry> dict) {
        LinkedList<DictEntry> results = new LinkedList<>();
        for (DictEntry item : dict) {
            results.addAll(splitter.split(item));
        }
        return results;
    }
}
