/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordcomparison;

import java.util.List;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.util.ListUtils;

/**
 *
 * @author marcin
 */
public class GrammaticalComparer implements IWordComparer {

    @Override
    public boolean areSimillar(DictEntry entry1, DictEntry entry2) {
        boolean sameKanji = sameKanjiSet(entry1, entry2);
        boolean differOnlyLast = differOnlyLastCharacter(entry1.serializedReadings(), entry2.serializedReadings());
        return sameKanji && differOnlyLast;
    }

    private boolean sameKanjiSet(DictEntry entry1, DictEntry entry2) {
        List<Character> kanji1 = Classifier.classify(entry1.serializedKeywords()).getKanji();
        List<Character> kanji2 = Classifier.classify(entry2.serializedKeywords()).getKanji();
        return ListUtils.areListsSame(kanji1, kanji2);
    }

    private boolean differOnlyLastCharacter(String first, String second) {
        int sizeToMatch = first.length() - 1;
        if (first.length() > second.length()) {
            sizeToMatch = second.length() - 1;
        }
        return first.regionMatches(0, second, 0, sizeToMatch);
    }

}
