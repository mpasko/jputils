/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mpasko.util.MapList;

/**
 *
 * @author marcin
 */
public class Classifier {

    public static Statistics classify(String text) {
        Statistics statistics = new Statistics();
        for (char c : text.toCharArray()) {
            statistics.classify(c);
        }
        return statistics;
    }

    public static Statistics classify(char c) {
        return classify(new Character(c).toString());
    }

    public static class Statistics {

        Map<Character.UnicodeBlock, Integer> map = new HashMap<Character.UnicodeBlock, Integer>();
        MapList<Character.UnicodeBlock, Character> particular = new MapList<>();
        int cnt = 0;

        public Statistics() {
        }

        public void classify(char c) {
            Character.UnicodeBlock unified = unify(Character.UnicodeBlock.of(c));
            particular.add(unified, c);
            increment(unified);
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

        public Character.UnicodeBlock unify(Character.UnicodeBlock block) {
            if (isKanji(block)) {
                return Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS;
            } else if (isLatin(block)) {
                return Character.UnicodeBlock.BASIC_LATIN;
            } else if (isKatakana(block)) {
                return Character.UnicodeBlock.KATAKANA;
            }
            return block;
        }

        public boolean containsKanji() {
            boolean basic = contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C);
            basic |= contains(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D);
            return basic;
        }

        public static boolean isKanji(Character.UnicodeBlock block) {
            return block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                    || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                    || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                    || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                    || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D;
        }

        public boolean containsFurigana() {
            return contains(Character.UnicodeBlock.HIRAGANA) | containsKatakana();
        }

        public boolean containsJapanese() {
            return containsFurigana() || containsKanji();
        }

        public boolean containsRomaji() {
            return contains(Character.UnicodeBlock.LATIN_1_SUPPLEMENT)
                    || contains(Character.UnicodeBlock.BASIC_LATIN)
                    || contains(Character.UnicodeBlock.LATIN_EXTENDED_A)
                    || contains(Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL)
                    || contains(Character.UnicodeBlock.LATIN_EXTENDED_B)
                    || contains(Character.UnicodeBlock.LATIN_EXTENDED_C)
                    || contains(Character.UnicodeBlock.LATIN_EXTENDED_D);
        }

        public static boolean isLatin(Character.UnicodeBlock block) {
            return block == Character.UnicodeBlock.LATIN_1_SUPPLEMENT
                    || block == Character.UnicodeBlock.BASIC_LATIN
                    || block == Character.UnicodeBlock.LATIN_EXTENDED_A
                    || block == Character.UnicodeBlock.LATIN_EXTENDED_ADDITIONAL
                    || block == Character.UnicodeBlock.LATIN_EXTENDED_B
                    || block == Character.UnicodeBlock.LATIN_EXTENDED_C
                    || block == Character.UnicodeBlock.LATIN_EXTENDED_D;
        }

        public boolean containsKatakana() {
            boolean basic = contains(Character.UnicodeBlock.KATAKANA);
            basic |= contains(Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS);
            return basic;
        }

        public static boolean isKatakana(Character.UnicodeBlock block) {
            return block == Character.UnicodeBlock.KATAKANA
                    || block == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS;
        }

        public List<Character> getKanji() {
            return particular.get(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
        }
    }
}
