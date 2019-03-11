package org.mpasko.loadres;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;

public class AllDictionaries {
    public static Dictionary load() {
        Dictionary jmdict = JmDictLoader.loadDictionary();
        addEntriesFromFile(jmdict, "dictionaries/jlpt_grammar.txt");
        addEntriesFromFile(jmdict, "dictionaries/character_names.txt");
        return jmdict;
    }

    private static void addEntriesFromFile(Dictionary jmdict, String filename) {
        Dictionary jlptGrammar = new DictionaryFileLoader()
                .loadTripleDict(filename);
        jmdict.putAll(jlptGrammar.getDict());
    }
}
