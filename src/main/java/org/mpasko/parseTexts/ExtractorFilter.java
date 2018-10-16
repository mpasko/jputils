package org.mpasko.parseTexts;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordfilters.*;
import org.mpasko.util.SimpleUtils;

import java.util.List;

public class ExtractorFilter {

    public Dictionary filterItems(List<String> items, Dictionary fulldict, Dictionary whiteReading, Dictionary whiteListening) {
        Dictionary input = findAndFilterItemsFromDictionary(items, "", fulldict);
        input = notIn(whiteListening).filter(input);
        input = notIn(whiteReading).filter(input);
        return input;
    }

    public static Dictionary findAndFilterItemsFromDictionary(List<String> items, final String rawText, Dictionary dict) {
        //ArrayList<Entry<String, String>> itemsOrdered = new ArrayList(items.entrySet());
        //itemsOrdered.sort(new TextPositionComparator(rawText));
        Dictionary found = findAllItems(items, dict);
        DuplicateFilter duplicateFilter = DuplicateFilter.outputDictionaryDuplicateFilter();
        found = duplicateFilter.filter(found);
        found = KnownWordsFilter.build().filter(found);
        return found;
    }

    public static Dictionary findAllItems(List<String> itemsOrdered, Dictionary dict) {
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
        return new ItemExistsInDictionary(dict.getDict());
    }
}
