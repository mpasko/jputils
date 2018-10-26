/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.parseTexts.splitters;

import java.util.*;
import java.util.stream.Collectors;

import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.parseTexts.extractors.BruteForceExtractor;
import org.mpasko.parseTexts.stemmers.AggregateStemmer;

/**
 *
 * @author marcin
 */
public class DictionarySplitter implements ISplitter {

    private final Dictionary dictionary;

    private int splitFrom = 2;
    private int splitUpTo = 3;
    private final String prefixes;
    private final String postfixes;

    public DictionarySplitter(Dictionary splitableDictionary, String prexixes, String postfixes) {
        this.dictionary = splitableDictionary;
        this.prefixes = prexixes;
        this.postfixes = postfixes;
    }

    public DictionarySplitter setLimits(int from, int to) {
        splitFrom = from;
        splitUpTo = to;
        return this;
    }

    @Override
    public List<DictEntry> split(DictEntry word) {
        if (word.kanji.length() <= 3) {
            return Arrays.asList(word);
        }
        if (Classifier.classify(word.kanji).getKanji().size() < word.kanji.length()) {
            return Arrays.asList(word);
        }
        List<DictEntry> extracted = new BruteForceExtractor(new AggregateStemmer(prefixes, postfixes), dictionary)
                .setLimits(splitFrom, splitUpTo)
                .extract(word.kanji)
                .stream().map(inflection -> inflection.dictionaryWord)
                .collect(Collectors.toList());
        if (coversAllCharsFromSource(extracted, word)) {
            return extracted;
        } else {
            return Arrays.asList(word);
        }
    }

    private static boolean coversAllCharsFromSource(List<DictEntry> split, DictEntry word) {
        Set<Character> splitSet = new HashSet<>();
        Set<Character> wordSet = charactersIntoSet(word.kanji);
        split.stream()
                .forEach(candidate -> splitSet.addAll(charactersIntoSet(candidate.kanji)));
        return splitSet.containsAll(wordSet);
    }

    private static Set<Character> charactersIntoSet(String word) {
        Set<Character> wordSet = new HashSet<Character>();
        for(char character : word.toCharArray()) {
            wordSet.add(character);
        }
        return wordSet;
    }

    //Todo move into separate class
    private List<String> splitEvenly(String wordToSplit) {
        LinkedList<String> result = new LinkedList<>();
        for (int index = 0; index < wordToSplit.length(); index += 2) {
            final String currentSplit = wordToSplit.substring(index, index + 2);
            result.add(currentSplit);
        }
        return result;
    }
}
