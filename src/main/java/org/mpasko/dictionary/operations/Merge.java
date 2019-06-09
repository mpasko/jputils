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
        DictEntry existingAlready = base.findDefault(additional.serializedKeywords());
        if (existingAlready == null) {
            base.put(additional);
        } else {
            if (!existingAlready.serializedMeanings().contains(additional.serializedMeanings())) {
                existingAlready.english = existingAlready.serializedMeanings() + "," + additional.serializedMeanings();
            }
            if (!existingAlready.serializedReadings().contains(additional.serializedReadings())) {
                existingAlready.writing = existingAlready.serializedReadings() + "," + additional.serializedReadings();
            }
        }
    }
}
