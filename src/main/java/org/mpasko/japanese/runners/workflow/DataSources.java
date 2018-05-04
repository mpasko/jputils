/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.analizers.ReadingDecomposer;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.DictionaryReconstructor;
import org.mpasko.dictionary.formatters.IFeatureChooser;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.dictionary.operations.Sum;
import org.mpasko.japanese.wordfilters.InversionOf;
import org.mpasko.japanese.wordfilters.OnyomiSpeculationFilter;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class DataSources {

    private Dictionary globalDictionary;

    public DataSources() {
        init();
    }

    private void init() {
        this.globalDictionary = JmDictLoader.loadDictionary();
    }

    public Dictionary getGlobalDict() {
        return globalDictionary;
    }

    public Dictionary listeningWhitelist() {
        Dictionary listening = reconstructListeningWhitelist();
        System.out.println("Listening reconstructed: " + listening.size());
        Dictionary speculation = loadWhitelistSpeculation();
        System.out.println("Speculation reconstructed: " + speculation.size());
        Dictionary result = new Sum(listening, speculation).result();
        System.out.println("Listening result reconstructed: " + result.size());
        return result;
    }

    public Dictionary loadWhitelistSpeculation() {
        List<DictEntry> speculation = loadAllSources();
        InversionOf filter = new InversionOf(OnyomiSpeculationFilter.initializeDefault());
        return filter.filter(new Dictionary(speculation));
    }

    public static List<String> getGlobalSourceList() {
        return Util.getSubdirectories(DefaultConfig.globalSources)
                .stream()
                .filter(dir -> !dir.startsWith("_"))
                .collect(Collectors.toList());
    }

    private List<DictEntry> loadAllSources() {
        List<DictEntry> speculation = getGlobalSourceList()
                .stream()
                .map(dir -> new DictionaryFileLoader()
                .loadTripleDictFromFolder(String.format("%s/%s", DefaultConfig.globalSources, dir)).items())
                .reduce(ReadingDecomposer::mergeLists)
                .get();
        return speculation;
    }

    private Dictionary reconstructListeningWhitelist() {
        Dictionary listening = reconstructListening(DefaultConfig.listeningWhitelist);
        return listening;
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

    public Dictionary readingWhitelist() {
        Dictionary reading = reconstructReading(DefaultConfig.readingWhitelist);
        System.out.println("Reading reconstructed: " + reading.size());
        return reading;
    }

    public Dictionary readingBlacklist() {
        return reconstructReading(DefaultConfig.readingBlacklist);
    }

    public Dictionary listeningBlacklist() {
        return reconstructListening(DefaultConfig.listeningBlacklist);
    }

    private Dictionary reconstruct(IFeatureChooser exact,
            IFeatureChooser contain,
            String path) {
        return new DictionaryReconstructor(globalDictionary, exact, contain)
                .reconstruct(Util.loadFilesInDirectory(path));
    }

    Dictionary globalDictionary() {
        return globalDictionary;
    }
}
