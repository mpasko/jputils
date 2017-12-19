/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.DictEntry;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.japanese.wordcomparison.SameKanji;
import org.mpasko.loadres.PopularDictionaries;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class KnownWordsFilter extends GenericFilter {

    List<DictEntry> knownWords = new LinkedList<DictEntry>();

    public static KnownWordsFilter build() {
        KnownWordsFilter filter = new KnownWordsFilter();
        filter.initialize();
        return filter;
    }

    @Override
    public boolean itemMatches(DictEntry entry) {
        /*
        if (entry.kanji.equals("おばあさん") || entry.writing.equals("おばあさん")) {
            System.out.println(entry);
        }
         */
        return !new ItemExistsInDictionary().exists(entry, knownWords, new SameKanji());
    }

    public void initialize() {
        Dictionary exceptions = new DictionaryFileLoader().loadTripleDict(DefaultConfig.exceptions);
        Dictionary goethe = PopularDictionaries.loadGoetheDictionaries();
        addList(goethe, exceptions);
        System.out.println("Known filter initialized with goethe");
        addList("dictionaries/some_songs.txt", exceptions);
        addList("dictionaries/jlptN5.txt", exceptions);
        addList("dictionaries/jlptN4.txt", exceptions);
        addList(DefaultConfig.whitelistFromSongs, exceptions);
        addListWithoutTranslations("inputs/whitelist_furigana.txt");
        addListWithoutTranslations("inputs/whitelist_words.txt");
        addList(DefaultConfig.processedManualWhitelist, exceptions);
    }

    private void addList(String filename, Dictionary exceptions) {
        Dictionary dict = new DictionaryFileLoader().loadTripleDict(filename);
        addList(dict, exceptions);
        System.out.println("Known filter initialized with " + filename);
    }

    private void addList(Dictionary goethe, Dictionary exceptions) {
        goethe.items().forEach((item) -> {
            if (!IsContainedInExceptions(item, exceptions)) {
                knownWords.add(item);
            }
        });
    }

    private boolean IsContainedInExceptions(DictEntry item, Dictionary exceptions) {
        boolean allow = false;
        for (DictEntry except : exceptions.items()) {
            if (item.kanji.equalsIgnoreCase(except.kanji)) {
                allow = true;
            }
        }
        return allow;
    }

    private void addListWithoutTranslations(String filename) {
        String content = Util.loadFile(filename);
        for (String line : content.split("\n")) {
            DictEntry item = new DictEntry(line, "none");
            knownWords.add(item);
        }
    }

}
