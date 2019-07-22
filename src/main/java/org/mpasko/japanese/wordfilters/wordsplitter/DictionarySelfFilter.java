package org.mpasko.japanese.wordfilters.wordsplitter;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.dictionary.LightDictionary;
import org.mpasko.japanese.wordfilters.IFilter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class DictionarySelfFilter implements IFilter {

    @Override
    public Dictionary filter(Dictionary dict) {
        return new Dictionary(filterInternal(dict.items()));
    }

    @Override
    public List<DictEntry> filter(List<DictEntry> dict) {
        return filterInternal(dict);
    }

    private List<DictEntry> filterInternal(List<DictEntry> dict) {
        LightDictionary raw = new LightDictionary();
        raw.putAll(dict);
        List<DictEntry> result = new LinkedList<>();
        for (DictEntry entry: dict) {
            if(!wordIsBuitFromExisting(entry, raw)) {
                result.add(entry);
            }
        }
        return result;
    }

    private boolean wordIsBuitFromExisting(DictEntry entry, LightDictionary raw) {
        if (entry.serializedKeywords().length() <= 3) {
            return false;
        }
        int start;
        for (start=0; start<entry.serializedKeywords().length()-3; start += 2) {
            if (!existsInRaw(entry.serializedKeywords().substring(start, start+2), raw)) {
                return false;
            }
        }
        return existsInRaw(entry.serializedKeywords().substring(start, entry.serializedKeywords().length()), raw);
    }

    private boolean existsInRaw(String keyword, LightDictionary raw) {
        return raw.find(keyword, "") != null;
    }
}
