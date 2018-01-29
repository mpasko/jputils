/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.List;
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

    void init() {
        this.globalDictionary = JmDictLoader.loadDictionary();
    }

    public Dictionary listeningWhitelist() {
        Dictionary listening = reconstructListeningWhitelist();
        System.out.println("Listening reconstructed: " + listening.size());
        Dictionary speculation = loadWhitelistSpeculation();
        System.out.println("Speculation reconstructed: " + listening.size());
        Dictionary result = new Sum(listening, speculation).result();
        System.out.println("Listening result reconstructed: " + listening.size());
        return result;
    }

    public Dictionary loadWhitelistSpeculation() {
        List<DictEntry> speculation = loadAllSources();
        InversionOf filter = new InversionOf(OnyomiSpeculationFilter.initializeDefault());
        return filter.filter(new Dictionary(speculation));
    }

    private List<DictEntry> loadAllSources() {
        List<DictEntry> speculation = DefaultConfig.selectedSources
                .entrySet()
                .stream()
                .map(entry -> new DictionaryFileLoader()
                .loadTripleDictFromFolder(entry.getValue()).items())
                .reduce(ReadingDecomposer::mergeLists)
                .get();
        return speculation;
    }

    private Dictionary reconstructListeningWhitelist() {
        Dictionary listening = reconstruct(globalDictionary,
                new WritingChooser(),
                new MeaningChooser(),
                DefaultConfig.listeningWhitelist);
        return listening;
    }

    public Dictionary readingWhitelist() {
        Dictionary reading = reconstruct(globalDictionary,
                new KanjiChooser(),
                new RomajiWritingChooser(),
                DefaultConfig.readingWhitelist);
        System.out.println("Reading reconstructed: " + reading.size());
        return reading;
    }

    private Dictionary reconstruct(Dictionary fullDictionary,
            IFeatureChooser exact,
            IFeatureChooser contain,
            String path) {
        return new DictionaryReconstructor(fullDictionary, exact, contain)
                .reconstruct(Util.loadFilesInDirectory(path));
    }

    Dictionary globalDictionary() {
        return globalDictionary;
    }
}
