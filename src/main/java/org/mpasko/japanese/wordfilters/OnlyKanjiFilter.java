/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class OnlyKanjiFilter extends GenericFilter{

    boolean hiragana = false;

    @Deprecated
    public static OnlyKanjiFilter katakanaFilter() {
        OnlyKanjiFilter filter = new OnlyKanjiFilter();
        filter.hiragana = true;
        return filter;
    }
    
    @Override
    public boolean itemMatches(DictEntry entry) {
        if (hiragana) {
            return !Classifier.classify(entry.serializedKeywords()).containsKatakana();
        } else {
            return !Classifier.classify(entry.serializedKeywords()).containsFurigana();
        }
    }
    
}
