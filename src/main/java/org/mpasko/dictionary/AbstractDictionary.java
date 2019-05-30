package org.mpasko.dictionary;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.formatters.DictionaryFormatter;
import org.mpasko.util.Filesystem;
import org.mpasko.util.ImmutableList;

import java.util.List;

public class AbstractDictionary implements IDictionary {
    protected List<DictEntry> dict;
    protected boolean pristine = false;

    @Override
    public void put(String kanji, String writing, String english) {
        put(new DictEntry(kanji, writing, english));
    }

    @Override
    public void put(String kanji, String english) {
        put(new DictEntry(kanji, english));
    }

    @Override
    public void put(DictEntry item) {
        dict.add(item);
        this.pristine = false;
    }

    @Override
    public void addAll(Dictionary dict) {
        this.dict.addAll(dict.items());
        this.pristine = false;
    }

    @Override
    public int size() {
        return this.dict.size();
    }

    @Override
    public void write(String filename) {
        new Filesystem().saveFile(filename, toString());
    }

    @Override
    public String toString() {
        return DictionaryFormatter.buildStandardFormatter().format(this);
    }

    @Override
    public void putAll(List<DictEntry> found) {
        dict.addAll(found);
        this.pristine = false;
    }

    @Override
    public List<DictEntry> items() {
        return new ImmutableList<>(dict);
    }

    @Override
    public DictEntry find(String kanji, String reading) {
        return null;
    }

    @Override
    public DictEntry findStrict(String kanji, String reading) {
        return null;
    }
}
