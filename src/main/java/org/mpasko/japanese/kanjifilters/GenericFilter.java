/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.kanjifilters;

import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.KanjiEntry;


/**
 *
 * @author marcin
 */
public abstract class GenericFilter {
    
    /*public static GenericFilter buildStandardFilter() {
        return new GenericFilter();
    }*/

    public KanjiDictionary filter(KanjiDictionary dict) {
        KanjiDictionary filtered = new KanjiDictionary();
        for (KanjiEntry item : dict.getItems()) {
            if (itemMatches(item)) {
                filtered.put(item);
                System.out.println(String.format("Accepted item: %s", item.character));
            }
        }
        return filtered;
    }
    
    public abstract boolean itemMatches(KanjiEntry entry);
}
