package org.mpasko.loadres;

import org.mpasko.configuration.FileConfigLoader;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.dictionary.operations.Merge;
import org.mpasko.japanese.wordfilters.CompoundFilter;
import org.mpasko.japanese.wordfilters.OnlyKanjiFilter;
import org.mpasko.japanese.wordfilters.wordsplitter.DictionarySelfFilter;

public class AllDictionaries {
    public static Dictionary load() {
        Dictionary dictionary = new Dictionary();
        Dictionary jmdict = filterDictionary(JmDictLoader.loadDictionary());
        new Merge().mergeDictionaries(dictionary, jmdict);
        addEntriesFromFile(dictionary, "dictionaries/jlpt_grammar.txt");
        addEntriesFromFile(dictionary, "dictionaries/character_names.txt");
        addEmdictIfNeeded(dictionary);
        return dictionary;
    }

    public static void addEmdictIfNeeded(Dictionary dictionary) {
        boolean emdictEnabled = new FileConfigLoader().loadConfig().dictionarySources.enableNameDictionary;
        if (emdictEnabled) {
            Dictionary emdict = filterDictionary(EmDictLoader.loadDictionary());
            new Merge().mergeDictionaries(dictionary, emdict);
        }
    }

    private static void addEntriesFromFile(IDictionary jmdict, String filename) {
        Dictionary dictionaryFromFile = new DictionaryFileLoader()
                .loadTripleDict(filename);
        new Merge().mergeDictionaries(jmdict, dictionaryFromFile);
    }

    private static Dictionary filterDictionary(Dictionary dict) {
        return new CompoundFilter(OnlyKanjiFilter.katakanaFilter())
                .and(new DictionarySelfFilter())
                .filter(dict);
    }
}
