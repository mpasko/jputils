package org.mpasko.dictionary;

import org.mpasko.commons.DictEntry;

import java.util.List;

public interface IDictionary {
    void put(String kanji, String writing, String english);

    void put(String kanji, String english);

    void put(DictEntry item);

    void addAll(Dictionary dict);

    int size();

    void write(String filename);

    String toString();

    void putAll(List<DictEntry> found);

    List<DictEntry> items();

    DictEntry find(String kanji, String reading);

    DictEntry findStrict(String kanji, String reading);

    DictEntry findDefault(String kanji);
}
