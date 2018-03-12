/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons.analizers;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.formatters.KanjiChooser;

/**
 *
 * @author marcin
 */
public class DictionarySplitter implements ISplitter {

    private final Dictionary dictionary;
    private final String prefixes;
    private final String suffixes;
    private final GrammarStemmer grammarStemmer = new GrammarStemmer();
    private List<String> particles;

    public DictionarySplitter(Dictionary splitableDictionary, String prefixes, String suffixes) {
        this.dictionary = splitableDictionary;
        this.prefixes = prefixes;
        this.suffixes = suffixes;
        seedParticles();
    }

    public List<DictEntry> splitText(String text) {
        final String wordToSplit = removeAllCharsFrom(text, prefixes + suffixes);
        int pivot = 0;
        LinkedList<DictEntry> potentialResult = new LinkedList<>();
        while (pivot < wordToSplit.length()) {
            if (isParticle(wordToSplit.substring(pivot, pivot + 1))) {
                pivot++;
            }
            DictEntry found = findInTheMiddle(wordToSplit, pivot);
            if (found == null) {
                //return potentialResult;
                pivot++;
            } else {
                potentialResult.add(found);
                pivot += found.kanji.length();
            }
        }
        return potentialResult;
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
        /*
        if (wordToSplit.length() % 2 == 0) {
            LinkedList<DictEntry> potentialResult = splitEvenly(wordToSplit)
                    .stream()
                    .map(current -> findFor(current))
                    .filter(item -> item != null)
                    .collect(new DictEntryCollector());
            if (potentialResult.size() == wordToSplit.length() / 2) {
                return potentialResult;
            }
        }*/
        //TODO reuse splitText
        int pivot = 0;
        LinkedList<DictEntry> potentialResult = new LinkedList<>();
        while (pivot < wordToSplit.length()) {
            if (isParticle(wordToSplit.substring(pivot, pivot + 1))) {
                pivot++;
            }
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

    private int splitFrom = 2;
    private int splitUpTo = 3;

    public DictionarySplitter setLimits(int from, int to) {
        splitFrom = from;
        splitUpTo = to;
        return this;
    }

    private DictEntry findInTheMiddle(String wordToSplit, int pivot) {
        DictEntry found = null;
        int rest = wordToSplit.length() - pivot;
        if (rest < splitFrom) {
            return null;
        }
        int maxPossibleSplit = Math.min(rest, splitUpTo);
        for (int acceptedLength = maxPossibleSplit; acceptedLength >= splitFrom; --acceptedLength) {
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
        final String wordToFind = grammarStemmer.stem(currentAnalyzed);
        return dictionary.findShortestByFeature(wordToFind, new KanjiChooser());
    }

    private boolean isParticle(String fragment) {
        return particles
                .stream()
                .anyMatch(particle -> fragment.equalsIgnoreCase(particle));
    }

    private void seedParticles() {
        particles = Arrays.asList("は", "へ", "を", "の", "に", "と", "や", "で");
    }

}
