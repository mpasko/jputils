/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import org.mpasko.japanese.dictionaries.SplitterSerializer;
import org.mpasko.japanese.wordcomparison.SynonimeComparer;
import org.mpasko.japanese.dictionaries.IDictEntry;

import java.util.List;

/**
 *
 * @author marcin
 */
public class DictEntry implements IDictEntry{

    /**
     * Deprecated, please use interface methods instead
     */
    @Deprecated
    public String kanji = "";
    /**
     * Deprecated, please use interface methods instead
     */
    @Deprecated
    public String writing = "";
    /**
     * Deprecated, please use interface methods instead
     */
    @Deprecated
    public String english = "";

    public DictEntry(String kanji, String writing, String english) {
        this.english = english;
        this.kanji = kanji;
        this.writing = writing;
    }

    public DictEntry(String kanji, String english) {
        this(kanji, kanji, english);
    }

    @Deprecated
    public boolean isSimillar(DictEntry entry) {
        if (equals(entry)) {
            return false;
        }
        //This is done by hasCommonKanji
        /*if (entry.kanji.contains("～")) {
         return this.kanji.contains("～");
         }*/
        if (writing.isEmpty() || entry.writing.isEmpty()) {
            return false;
        }
        if (hasCommonKanjiWith(entry.kanji)) {
            return true;
        }
        if (Classifier.classify(writing).containsFurigana()) {
            char lastThis = writing.charAt(writing.length() - 1);
            char lastOther = entry.writing.charAt(entry.writing.length() - 1);
            return lastThis == lastOther;
        }
        return false;
    }

    @Deprecated
    public boolean isColliding(DictEntry entry) {
        return entry.kanji.contains(this.kanji)
                || this.kanji.contains(entry.kanji)
                || new SynonimeComparer().areSimillar(entry, this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.kanji != null ? this.kanji.hashCode() : 0);
        hash = 11 * hash + (this.writing != null ? this.writing.hashCode() : 0);
        hash = 11 * hash + (this.english != null ? this.english.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DictEntry other = (DictEntry) obj;
        if ((this.kanji == null) ? (other.kanji != null) : !this.kanji.equals(other.kanji)) {
            return false;
        }
        if ((this.writing == null) ? (other.writing != null) : !this.writing.equals(other.writing)) {
            return false;
        }
        if ((this.english == null) ? (other.english != null) : !this.english.equals(other.english)) {
            return false;
        }
        return true;
    }

    public String generateQuestion() {
        if (kanji.equals(writing)) {
            return kanji;
        }
        return kanji + " " + writing;
    }

    @Deprecated
    private boolean hasCommonKanjiWith(String kanji) {
        char[] oppositeArray = kanji.toCharArray();
        for (char k1 : this.kanji.toCharArray()) {
            if (Classifier.classify(k1).containsKanji()) {
                for (char k2 : oppositeArray) {
                    if (k1 == k2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder all = new StringBuilder();
        all.append(this.kanji);
        all.append(" ");
        all.append(this.writing);
        all.append(" -");
        all.append(this.english);
        return all.toString();
    }

    @Override
    public List<String> getKeywords() {
        return SplitterSerializer.split(kanji, ",;");
    }

    @Override
    public List<String> getReadings() {
        return SplitterSerializer.split(writing, ",;");
    }

    @Override
    public List<String> getMeanings() {
        return SplitterSerializer.split(english, ",;");
    }

    @Override
    public String serializedKeywords() {
        return this.kanji;
    }

    @Override
    public String serializedReadings() {
        return this.writing;
    }

    @Override
    public String serializedMeanings() {
        return this.english;
    }
}
