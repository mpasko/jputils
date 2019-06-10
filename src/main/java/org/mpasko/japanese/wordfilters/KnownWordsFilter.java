/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.DictEntry;
import org.mpasko.configuration.DefaultPaths;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.formatters.IFeatureChooser;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.util.Filesystem;

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
    private Dictionary knownDictionary;

    @Override
    public boolean itemMatches(DictEntry entry) {
        /*
        if (entry.kanji.equals("おばあさん") || entry.writing.equals("おばあさん")) {
            System.out.println(entry);
        }
         */
        //return !new ItemExistsInDictionary().exists(entry, knownWords, new SameKanji());
        if (wordCrossMatches(entry, new KanjiChooser(), new KanjiChooser())) {
            return false;
        }
        if (wordCrossMatches(entry, new WritingChooser(), new KanjiChooser())) {
            return false;
        }
        if (wordCrossMatches(entry, new KanjiChooser(), new WritingChooser())) {
            return false;
        }
        return true;
    }

    private boolean wordCrossMatches(DictEntry entry, IFeatureChooser wordFeature, IFeatureChooser dictFeature) {
        String pulledFromWord = wordFeature.choose(entry);
        LinkedList<DictEntry> found = knownDictionary.findAllByFeature(pulledFromWord, dictFeature);
        return !found.isEmpty();
    }

    public void initialize() {
        addList("dictionaries/jlptN5.txt", new Dictionary());
        addList("dictionaries/jlptN4.txt", new Dictionary());
        /*
        Dictionary exceptions = new DictionaryFileLoader().loadTripleDict(DefaultPaths.exceptions);
        Dictionary goethe = PopularDictionaries.loadGoetheDictionaries();
        addList(goethe, exceptions);
        System.out.println("Known filter initialized with goethe");
        addList("dictionaries/some_songs.txt", exceptions);
        addList(DefaultPaths.whitelistFromSongs, exceptions);
        addList(DefaultPaths.whitelistFromSongsNew, exceptions);
        addListWithoutTranslations("inputs/whitelist_furigana.txt");
        addListWithoutTranslations("inputs/whitelist_words.txt");
        */
        Dictionary understandingDictionary = new DictionaryFileLoader().loadTripleDictFromFolder(DefaultPaths.understandingWhitelist);
        knownWords.addAll(understandingDictionary.items());
        this.knownDictionary = new Dictionary(knownWords);
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
            if (item.serializedKeywords().equalsIgnoreCase(except.serializedKeywords())) {
                allow = true;
            }
        }
        return allow;
    }

    private void addListWithoutTranslations(String filename) {
        String content = new Filesystem().loadFile(filename);
        for (String line : content.split("\n")) {
            DictEntry item = new DictEntry(line, "none");
            knownWords.add(item);
        }
    }

}
