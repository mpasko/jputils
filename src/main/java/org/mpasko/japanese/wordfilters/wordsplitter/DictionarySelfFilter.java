package org.mpasko.japanese.wordfilters.wordsplitter;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordfilters.IFilter;

import java.util.ArrayList;
import java.util.Comparator;

public class DictionarySelfFilter implements IFilter {

    @Override
    public Dictionary filter(Dictionary dict) {
        ArrayList<DictEntry> raw = new ArrayList<>(dict.getDict());
        raw.sort(Comparator.comparingInt(o -> o.kanji.length()));
        Dictionary result = new Dictionary();
        for (DictEntry entry: raw) {
            if(!wordIsBuitFromExisting(entry, raw)) {
                result.put(entry);
            }
        }
        return result;
    }

    private boolean wordIsBuitFromExisting(DictEntry entry, ArrayList<DictEntry> raw) {
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

    //Todo create lightweight dictionary and implement bisect search within
    private boolean existsInRaw(String keyword, ArrayList<DictEntry> raw) {
        return raw.stream()
                .anyMatch(item -> item.kanji.equalsIgnoreCase(keyword));
    }
}
