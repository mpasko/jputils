package org.mpasko.parseTexts;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.japanese.wordfilters.*;
import org.mpasko.util.SimpleUtils;

import java.util.List;

public class ExtractorFilter {

    private DuplicateFilter duplicateFilter;
    private KnownWordsFilter knownWordFilter;

    public ExtractorFilter() {
        buildFilters();
    }

    private void buildFilters() {
        //duplicateFilter = DuplicateFilter.outputDictionaryDuplicateFilter();
        knownWordFilter = KnownWordsFilter.build();
    }

    public IDictionary filterItems(List<String> items, Dictionary fulldict, Dictionary whiteReading, Dictionary whiteListening) {
        Dictionary input = findAndFilterItemsFromDictionary(items, fulldict);
        input = notIn(whiteListening).filter(input);
        input = notIn(whiteReading).filter(input);
        return input;
    }

    public Dictionary findAndFilterItemsFromDictionary(List<String> items, Dictionary dict) {
        //ArrayList<Entry<String, String>> itemsOrdered = new ArrayList(items.entrySet());
        //itemsOrdered.sort(new TextPositionComparator(rawText));
        Dictionary found = findAllItems(items, dict);
        duplicateFilter = DuplicateFilter.outputDictionaryDuplicateFilter();
        found = duplicateFilter.filter(found);
        found = knownWordFilter.filter(found);
        return found;
    }

    private static Dictionary findAllItems(List<String> itemsOrdered, Dictionary dict) {
        Dictionary found = new Dictionary();
        for (String pair : itemsOrdered) {
            DictEntry item = dict.find(pair, pair);
            if ((item != null)) {
                found.put(item.kanji, item.writing, SimpleUtils.limitComas(item.english));
            } else {
                System.out.println(pair + " -not found in dict!");
            }
        }
        return found;
    }

    private GenericFilter notIn(Dictionary dict) {
        return new InversionOf(in(dict));
    }

    private GenericFilter in(Dictionary dict) {
        return new ItemExistsInDictionary(dict.items());
    }
}
