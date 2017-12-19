/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.Comparator;
import java.util.LinkedList;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.formatters.IFeatureChooser;

/**
 *
 * @author marcin
 */
public class UniversalIndex {

    static Comparator<? super DictEntry> THE_SHORTEST_WRITING
            = (s1, s2) -> s1.writing.length() - s2.writing.length();

    private final IFeatureChooser feature;
    private MultipleIndexer<DictEntry> index;
    private final Dictionary dict;

    public UniversalIndex(IFeatureChooser feature, Dictionary dict) {
        this.feature = feature;
        this.dict = dict;
    }

    private void createIndexWhenNeeded() {
        if (index == null) {
            createIndex();
        }
    }

    public void createIndex() {
        index = new MultipleIndexer<>();
        dict.items()
                .stream()
                .forEach(entry -> updateIndex(entry));
    }

    public void updateIndex(DictEntry entry) {
        createIndexWhenNeeded();
        index.put(feature.choose(entry), entry);
    }

    public DictEntry findBest(DictEntry entry, Comparator<? super DictEntry> comparator) {
        createIndexWhenNeeded();
        String key = feature.choose(entry);
        return index.getBest(key, comparator);
    }

    public DictEntry findBest(DictEntry entry) {
        return findBest(entry, THE_SHORTEST_WRITING);
    }

    public LinkedList<DictEntry> findAll(DictEntry entry) {
        createIndexWhenNeeded();
        return index.getAll(feature.choose(entry));
    }
}
