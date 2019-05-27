package org.mpasko.loadres;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.japanese.wordfilters.CompoundFilter;
import org.mpasko.japanese.wordfilters.OnlyKanjiFilter;
import org.mpasko.japanese.wordfilters.wordsplitter.DictionarySelfFilter;

public class AllDictionaries {
    public static Dictionary load() {
        Dictionary dictionary = new Dictionary();
        Dictionary jmdict = filterDictionary(JmDictLoader.loadDictionary());
        dictionary.putAll(jmdict.items());
        addEntriesFromFile(dictionary, "dictionaries/jlpt_grammar.txt");
        addEntriesFromFile(dictionary, "dictionaries/character_names.txt");
        Dictionary emdict = filterDictionary(EmDictLoader.loadDictionary());
        dictionary.putAll(emdict.items());
        return dictionary;
    }

    private static void addEntriesFromFile(IDictionary jmdict, String filename) {
        Dictionary jlptGrammar = new DictionaryFileLoader()
                .loadTripleDict(filename);
        jmdict.putAll(jlptGrammar.items());
    }

    private static Dictionary filterDictionary(Dictionary dict) {
        return new CompoundFilter(OnlyKanjiFilter.katakanaFilter())
                .and(new DictionarySelfFilter())
                .filter(dict);
    }
}
