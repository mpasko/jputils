package org.mpasko.statistic.wordsFrequency;

import org.mpasko.dictionary.IDictionary;
import org.mpasko.japanese.dictionaries.IDictEntry;

import java.util.LinkedList;
import java.util.List;

public class FrequencyCountMap implements IFrequencyCountMap{

    @Override
    public void putNext(IDictEntry word) {

    }

    @Override
    public void putAll(List<? extends IDictEntry> words) {

    }

    @Override
    public List<IDictEntry> getMostFrequent(int topCount, IDictionary subset) {
        return new LinkedList<>();
    }
}
