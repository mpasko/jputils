package org.mpasko.dictionary.operations;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.IDictionary;

import java.util.List;

public class Merge {

    public IDictionary mergeDictionaries(IDictionary base, IDictionary additional) {
        return mergeDictionaries(base, additional.items());
    }

    public IDictionary mergeDictionaries(IDictionary base, List<DictEntry> additional) {
        additional
                .stream()
                .forEach(item -> mergeItem(base, item));
        return base;
    }

    public void mergeItem(IDictionary base, DictEntry additional) {
        DictEntry existingAlready = base.findDefault(additional.kanji);
        if (existingAlready == null) {
            base.put(additional);
        } else {
            if (!existingAlready.english.contains(additional.english)) {
                existingAlready.english = existingAlready.english + "," + additional.english;
            }
            if (!existingAlready.writing.contains(additional.writing)) {
                existingAlready.writing = existingAlready.writing + "," + additional.writing;
            }
        }
    }
}
