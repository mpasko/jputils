package org.mpasko.loadres;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;

public class AllDictionaries {
    public static Dictionary load() {
        Dictionary jmdict = JmDictLoader.loadDictionary();
        Dictionary jlptGrammar = new DictionaryFileLoader()
                .loadTripleDict("dictionaries/jlpt_grammar.txt");
        jmdict.putAll(jlptGrammar.getDict());
        return jmdict;
    }
}
