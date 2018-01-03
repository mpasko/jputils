/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.operations;

import java.util.LinkedList;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.UniversalIndex;
import org.mpasko.dictionary.formatters.KanjiWritingChooser;
import org.mpasko.util.collectors.DictEntryCollector;

/**
 *
 * @author marcin
 */
public class Product {

    private final Dictionary dict1;
    private final Dictionary dict2;

    public Product(Dictionary dict1, Dictionary dict2) {
        this.dict1 = dict1;
        this.dict2 = dict2;
    }

    public Dictionary result() {
        UniversalIndex index = new UniversalIndex(new KanjiWritingChooser(), dict1);
        final Dictionary resultDictionary = new Dictionary();
        LinkedList<DictEntry> result = dict2.items()
                .stream()
                .filter((entry) -> !index.findAll(entry).isEmpty())
                .collect(new DictEntryCollector());
        resultDictionary.putAll(result);
        return resultDictionary;
    }
}
