package org.mpasko.japanese.wordfilters.wordsplitter;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.dictionary.LightDictionary;
import org.mpasko.japanese.wordfilters.IFilter;

import java.util.ArrayList;
import java.util.Comparator;

public class DictionarySelfFilter implements IFilter {

    @Override
    public Dictionary filter(Dictionary dict) {
        return new Dictionary(filterInternal(dict));
    }

    private IDictionary filterInternal(IDictionary dict) {
        LightDictionary raw = new LightDictionary();
        raw.putAll(dict.items());
        Dictionary result = new Dictionary();
        for (DictEntry entry: dict.items()) {
            if(!wordIsBuitFromExisting(entry, raw)) {
                result.put(entry);
            }
        }
        return result;
    }

    private boolean wordIsBuitFromExisting(DictEntry entry, LightDictionary raw) {
        if (entry.kanji.length() <= 3) {
            return false;
        }
        int start;
        for (start=0; start<entry.kanji.length()-3; start += 2) {
            if (!existsInRaw(entry.kanji.substring(start, start+2), raw)) {
                return false;
            }
        }
        return existsInRaw(entry.kanji.substring(start, entry.kanji.length()), raw);
    }

    private boolean existsInRaw(String keyword, LightDictionary raw) {
        return raw.find(keyword, "") != null;
    }
}
