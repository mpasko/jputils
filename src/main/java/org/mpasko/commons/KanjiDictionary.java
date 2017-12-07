/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author marcin
 */
public class KanjiDictionary {
    List<KanjiEntry> dict = new LinkedList<KanjiEntry>();
    
    public void addItem(String kanji, String meaning, List<String> on, List<String> kun) {
        KanjiEntry kanjiEntry = new KanjiEntry();
        kanjiEntry.meaning = meaning;
        kanjiEntry.character = kanji;
        kanjiEntry.kunomi = kun;
        kanjiEntry.onyomi = on;
        dict.add(kanjiEntry);
    }
    
    public List<KanjiEntry> getItems() {
        return dict;
    }

    public void put(KanjiEntry item) {
        dict.add(item);
    }
    
    public String format() {
        StringBuilder build = new StringBuilder();
        for (KanjiEntry item : dict) {
            build.append(item.character).append(" - ");
            build.append(item.formatAnswer());
            build.append("\n");
        }
        return build.toString();
    }
}
