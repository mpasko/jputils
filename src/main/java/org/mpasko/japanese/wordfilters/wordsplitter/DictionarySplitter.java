/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters.wordsplitter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.util.collectors.DictEntryCollector;

/**
 *
 * @author marcin
 */
public class DictionarySplitter implements ISplitter {

    private final Dictionary dictionary;
    private final String prefixes;
    private final String suffixes;

    public DictionarySplitter(Dictionary splitableDictionary, String prefixes, String suffixes) {
        this.dictionary = splitableDictionary;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
    }

    @Override
    public List<DictEntry> split(DictEntry word) {
        if (word.kanji.length() <= 3) {
            return Arrays.asList(word);
        }
        if (Classifier.classify(word.kanji).getKanji().size() < word.kanji.length()) {
            return Arrays.asList(word);
        }
        final String wordToSplit = removeAllCharsFrom(word.kanji, prefixes + suffixes);
        if (wordToSplit.length() % 2 == 0) {
            LinkedList<DictEntry> potentialResult = splitEvenly(wordToSplit)
                    .stream()
                    .map(current -> findFor(current))
                    .filter(item -> item != null)
                    .collect(new DictEntryCollector());
            if (potentialResult.size() == wordToSplit.length() / 2) {
                return potentialResult;
            }
        }
        int pivot = 0;
        LinkedList<DictEntry> potentialResult = new LinkedList<>();
        while (pivot < wordToSplit.length()) {
            DictEntry found = findInTheMiddle(wordToSplit, pivot);
            if (found == null) {
                return Arrays.asList(word);
            } else {
                potentialResult.add(found);
                pivot += found.kanji.length();
            }
        }
        return potentialResult;
    }
    private static final int SPLIT_FROM = 2;
    private static final int SPLIT_UP = 3;

    private DictEntry findInTheMiddle(String wordToSplit, int pivot) {
        DictEntry found = null;
        int rest = wordToSplit.length() - pivot;
        if (rest < SPLIT_FROM) {
            return null;
        }
        int maxPossibleSplit = Math.min(rest, SPLIT_UP);
        for (int acceptedLength = maxPossibleSplit; acceptedLength >= SPLIT_FROM; --acceptedLength) {
            final String currentSplit = wordToSplit.substring(pivot, pivot + acceptedLength);
            found = findFor(currentSplit);
            if (found != null) {
                break;
            }
        }
        return found;
    }

    private String removeAllCharsFrom(String word, String chars) {
        String worked = word;
        for (char character : chars.toCharArray()) {
            worked = worked.replaceAll(new String(new char[]{character}), "");
        }
        return worked;
    }

    private List<String> splitEvenly(String wordToSplit) {
        LinkedList<String> result = new LinkedList<>();
        for (int index = 0; index < wordToSplit.length(); index += 2) {
            final String currentSplit = wordToSplit.substring(index, index + 2);
            result.add(currentSplit);
        }
        return result;
    }

    private DictEntry findFor(final String currentAnalyzed) {
        return dictionary.findShortestByFeature(currentAnalyzed, new KanjiChooser());
    }

}
