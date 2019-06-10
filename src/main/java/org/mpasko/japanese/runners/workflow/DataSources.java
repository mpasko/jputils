/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.analizers.ReadingDecomposer;
import org.mpasko.loadres.AllDictionaries;
import org.mpasko.management.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.DictionaryReconstructor;
import org.mpasko.dictionary.formatters.IFeatureChooser;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.dictionary.operations.Sum;
import org.mpasko.japanese.wordfilters.OnyomiSpeculationFilter;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class DataSources implements IDataSource {

    private Dictionary globalDictionary;

    public DataSources() {
        init();
    }

    private void init() {
        this.globalDictionary = AllDictionaries.load();
    }

    private Dictionary readingWhitelist() {
        Dictionary listening = reconstructReadingWhitelist();
        System.out.println("Reading reconstructed: " + listening.size());
        Dictionary speculation = loadWhitelistSpeculation();
        System.out.println("Speculation reconstructed: " + speculation.size());
        Dictionary result = new Sum(listening, speculation).result();
        System.out.println("Listening result reconstructed: " + result.size());
        return result;
    }

    public Dictionary loadWhitelistSpeculation() {
        List<DictEntry> speculation = loadAllWordsFromSources();

        OnyomiSpeculationFilter defaultOnyomiFilter = OnyomiSpeculationFilter.initializeDefault();
        //InversionOf filter = new InversionOf(defaultOnyomiFilter);
        return defaultOnyomiFilter.filter(new Dictionary(speculation));
    }

    public static List<String> getGlobalSourceList() {
        return new Filesystem().getSubdirectories(DefaultConfig.wordsGlobalSources)
                .stream()
                .filter(dir -> !dir.startsWith("_"))
                .collect(Collectors.toList());
    }

    private List<DictEntry> loadAllWordsFromSources() {
        List<DictEntry> speculation = getGlobalSourceList()
                .stream()
                .map(dir -> new DictionaryFileLoader()
                .loadTripleDictFromFolder(String.format("%s/%s", DefaultConfig.wordsGlobalSources, dir)).items())
                .reduce(ReadingDecomposer::mergeLists)
                .orElseGet(() -> new LinkedList<DictEntry>());
        return speculation;
    }

    private Dictionary listeningWhitelist() {
        return reconstructListening(DefaultConfig.listeningWhitelist);
    }

    private Dictionary reconstructListening(final String listeningPath) {
        Dictionary listening = reconstruct(new WritingChooser(),
                new MeaningChooser(),
                listeningPath);
        return listening;
    }

    private Dictionary reconstructReading(final String readingPath) {
        Dictionary listening = reconstruct(new KanjiChooser(),
                new RomajiWritingChooser(),
                readingPath);
        return listening;
    }

    private Dictionary reconstructReadingWhitelist() {
        return reconstructReading(DefaultConfig.readingWhitelist);
    }

    private Dictionary readingBlacklist() {
        return reconstructReading(DefaultConfig.readingBlacklist);
    }

    private Dictionary listeningBlacklist() {
        return reconstructListening(DefaultConfig.listeningBlacklist);
    }

    private Dictionary reconstruct(IFeatureChooser exact,
            IFeatureChooser contain,
            String path) {
        return new DictionaryReconstructor(globalDictionary, exact, contain)
                .reconstruct(new Filesystem().loadFilesInDirectory(path));
    }

    Dictionary globalDictionary() {
        return globalDictionary;
    }

    @Override
    public Dictionary getListeningWhitelist() {
        return listeningWhitelist();
    }

    @Override
    public Dictionary getReadingWhitelist() {
        return readingWhitelist();
    }

    @Override
    public Dictionary getListeningBlacklist() {
        return listeningBlacklist();
    }

    @Override
    public Dictionary getReadingBlacklist() {
        return readingBlacklist();
    }

    @Override
    public Dictionary getGlobalDictionary() {
        return globalDictionary;
    }
}
