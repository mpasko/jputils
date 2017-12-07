/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marcin
 */
public class Classifier {

    public static Statistics classify(String text) {
        Statistics statistics = new Statistics();
        for (char c : text.toCharArray()) {
            statistics.increment(Character.UnicodeBlock.of(c));
        }
        return statistics;
    }

    public static Statistics classify(char c) {
        Statistics statistics = new Statistics();
        statistics.increment(Character.UnicodeBlock.of(c));
        return statistics;
    }

    public static class Statistics {

        Map<Character.UnicodeBlock, Integer> map = new HashMap<Character.UnicodeBlock, Integer>();
        int cnt = 0;

        public Statistics() {
        }

        public void increment(Character.UnicodeBlock block) {
            ++cnt;
            Integer current = map.get(block);
            if (current == null) {
                current = 0;
            }
            map.put(block, current + 1);
        }

        public boolean contains(Character.UnicodeBlock block) {
            return map.get(block) != null;
        }

        public boolean containsKanji() {
            boolean basic = contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D);
            return basic;
        }

        public boolean containsFurigana() {
            return contains(Character.UnicodeBlock.HIRAGANA) | containsKatakana();
        }

        public boolean containsJapanese() {
            return containsFurigana() || containsKanji();
        }

        public boolean containsKatakana() {
            boolean basic = contains(Character.UnicodeBlock.KATAKANA);
            basic |= contains(Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS);
            return basic;
        }
    }
}
