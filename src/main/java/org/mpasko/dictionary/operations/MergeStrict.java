package org.mpasko.dictionary.operations;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;

import java.util.List;

/***
 *   Deprecated, preserving in order to backward compatibility
 *   Please use Merge instead
*/
@Deprecated
public class MergeStrict {

    public Dictionary mergeDictionaries(Dictionary base, Dictionary additional) {
        return mergeDictionaries(base, additional.items());
    }

    public Dictionary mergeDictionaries(Dictionary base, List<DictEntry> additional) {
        additional
                .stream()
                .forEach(item -> mergeItem(base, item));
        return base;
    }

    public void mergeItem(Dictionary base, DictEntry additional) {
        DictEntry existingAlready = base.findStrict(additional.serializedKeywords(), additional.serializedReadings());
        if (existingAlready == null) {
            base.put(additional);
        } else {
            if (!existingAlready.serializedMeanings().contains(additional.serializedMeanings())) {
                existingAlready.english = existingAlready.serializedMeanings() + "," + additional.serializedMeanings();
            }
        }
    }
}
