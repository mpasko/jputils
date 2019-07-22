package org.mpasko.statistic.wordsFrequency;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.parseTexts.WordsExtractor;

import java.util.List;

public class FrequencyCounter {
    public FrequencyCountMap count(String input, Dictionary fullDict) {
        FrequencyCountMap frequencyCountMap = new FrequencyCountMap();
        List<DictEntry> words = new WordsExtractor(fullDict)
                .extractFromText(input);
        frequencyCountMap.putAll(words);
        return frequencyCountMap;
    }
}
