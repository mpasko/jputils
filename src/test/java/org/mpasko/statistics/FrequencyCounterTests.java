package org.mpasko.statistics;

import org.junit.Assert;
import org.junit.Test;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.dictionaries.IDictEntry;
import org.mpasko.statistic.wordsFrequency.FrequencyCountMap;
import org.mpasko.statistic.wordsFrequency.FrequencyCounter;

import java.util.List;

public class FrequencyCounterTests {
    @Test
    public void shouldIdentifyMostFrequentWord() {
        FrequencyCounter frequencyCounter = new FrequencyCounter();
        Dictionary dictionary = new Dictionary();
        dictionary.put("麦", "flour");
        dictionary.put("生", "raw,crude");
        dictionary.put("米", "rice");
        FrequencyCountMap frequency = frequencyCounter.count("生米生麦生卵", dictionary);

        List<IDictEntry> listOfMostFrequent = frequency.getMostFrequent(1, dictionary);
        Assert.assertEquals("expected to return list of given size",1, listOfMostFrequent.size());
        IDictEntry mostFrequent = listOfMostFrequent.get(0);
        Assert.assertEquals("生", mostFrequent.getKeywords());
    }
}
