/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.kanjifilters;

import java.util.HashSet;
import java.util.Set;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.commons.KanjiEntry;

/**
 *
 * @author marcin
 */
public class DictFilter extends GenericFilter{
    private Set<String> kanjiSet = new HashSet<String>();
    
    public DictFilter(Dictionary dict) {
        withDict(dict);
    }

    public final DictFilter withDict(Dictionary dict) {
        for (DictEntry item : dict.items()) {
            for (char kanji : item.kanji.toCharArray()) {
                if (Classifier.classify(kanji).containsKanji()) {
                    kanjiSet.add(Character.toString(kanji));
                }
            }
        }
        return this;
    }

    @Override
    public boolean itemMatches(KanjiEntry entry) {
        return kanjiSet.contains(entry.character);
    }
}
