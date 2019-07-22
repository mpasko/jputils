package org.mpasko.statistic.wordsFrequency;

import org.mpasko.dictionary.IDictionary;
import org.mpasko.japanese.dictionaries.IDictEntry;

import java.util.List;

public interface IFrequencyCountMap {
    void putNext(IDictEntry word);
    void putAll(List<? extends IDictEntry> words);
    List<IDictEntry> getMostFrequent(int topCount, IDictionary subset);
}
