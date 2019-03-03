package org.mpasko.japanese.wordfilters;

import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;

public class NotEnglishOriginFilter extends GenericFilter {

    @Override
    public boolean itemMatches(DictEntry entry) {
        return !containsKatakana(entry) || isOnomatopeic(entry.kanji);
    }

    private boolean isOnomatopeic(String entry) {
        int length = entry.length();
        String firstHalf = entry.substring(0, length / 2 - 1);
        String secondHalf = entry.substring(length / 2, length - 1);
        return firstHalf.equals(secondHalf);
    }

    private boolean containsKatakana(DictEntry entry) {
        return Classifier.classify(entry.kanji).containsKatakana();
    }
}
