package org.mpasko.parseTexts.extractors;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.parseTexts.stemmers.IStemmer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteForceExtractor implements IExtractor{

    private int splitFrom = 2;
    private int splitUpTo = 3;
    private List<String> particles;
    private final IStemmer grammarStemmer;
    private Dictionary dictionary;

    public BruteForceExtractor(IStemmer grammarStemmer, Dictionary dictionary) {
        this.grammarStemmer = grammarStemmer;
        this.dictionary = dictionary;
        seedParticles();
    }

    public BruteForceExtractor setLimits(int from, int to) {
        splitFrom = from;
        splitUpTo = to;
        return this;
    }

    public List<Inflection> extract(String text) {
        int pivot = 0;
        LinkedList<Inflection> potentialResult = new LinkedList<>();
        while (pivot < text.length()) {
            if (isParticle(text.substring(pivot, pivot + 1))) {
                pivot++;
            }
            Inflection found = findInTheMiddle(text, pivot);
            if (found == null) {
                pivot++;
            } else {
                potentialResult.add(found);
                pivot += found.originalForm.length();
            }
        }
        return potentialResult;
    }

    private Inflection findInTheMiddle(String wordToSplit, int pivot) {
        Inflection found = null;
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

    private boolean isParticle(String fragment) {
        return particles
                .stream()
                .anyMatch(particle -> fragment.equalsIgnoreCase(particle));
    }

    private void seedParticles() {
        particles = Arrays.asList("は", "へ", "を", "の", "に", "と", "や", "で");
    }

    private Inflection findFor(final String currentAnalyzed) {
        final String wordToFind = grammarStemmer.stem(currentAnalyzed);
        final DictEntry found = dictionary.findShortestByFeature(wordToFind, new KanjiChooser());
        if (found != null) {
            return new Inflection(found, currentAnalyzed);
        }
        return null;
    }
}
