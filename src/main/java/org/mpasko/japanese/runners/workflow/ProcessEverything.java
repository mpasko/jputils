/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.DictionaryReconstructor;
import org.mpasko.dictionary.formatters.DictionaryFormatter;
import org.mpasko.dictionary.formatters.IFeatureChooser;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.dictionary.operations.Product;
import org.mpasko.japanese.wordfilters.DuplicateFilter;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.util.StringUtils;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class ProcessEverything {

    public static void main(String[] args) {
        new ProcessEverything().start();
    }
    private Dictionary globalDictionary;

    public void start() {
        generateListeningFromSelectedSources();
        generateReadingFromSelectedSources();
        mergeReadingAndListening();
    }

    private void generateReadingFromSelectedSources() {
        final DictionaryFormatter formatter = DictionaryFormatter.buildReadingFormatter();
        generateFromSelectedSourcesUsingFormatter(formatter, DefaultConfig.readingSources);
    }

    private void generateListeningFromSelectedSources() {
        final DictionaryFormatter formatter = DictionaryFormatter.buildListeningFormatter();
        generateFromSelectedSourcesUsingFormatter(formatter, DefaultConfig.listeningSources);
    }

    private void generateFromSelectedSourcesUsingFormatter(final DictionaryFormatter formatter, String outputDirectory) {
        DefaultConfig.selectedSources
                .entrySet()
                .stream()
                .forEach(entry
                        -> generateListeningFromSelectedSource(entry.getKey(), entry.getValue(), formatter, outputDirectory));
    }

    private void generateListeningFromSelectedSource(String category, String path, DictionaryFormatter formatter, String outputDirectory) {
        Dictionary dict = new DictionaryFileLoader().loadTripleDictFromFolder(path);
        saveAs(dict, formatter, outputDirectory, category);
    }

    private void saveAs(Dictionary dict, DictionaryFormatter formatter, String outputDirectory, String category) {
        dict = DuplicateFilter.outputDictionaryDuplicateFilter().filter(dict);
        String content = formatter.format(dict);
        final String filename = String.format("%s.txt", StringUtils.joinPath(outputDirectory, category));
        Util.saveFile(filename, content);
    }

    private void mergeReadingAndListening() {
        this.globalDictionary = JmDictLoader.loadDictionary();
        Dictionary reading = reconstruct(globalDictionary,
                new KanjiChooser(),
                new RomajiWritingChooser(),
                DefaultConfig.readingWhitelist);
        System.out.println("Reading reconstructed: " + reading.size());
        Dictionary listening = reconstruct(globalDictionary,
                new WritingChooser(),
                new MeaningChooser(),
                DefaultConfig.listeningWhitelist);
        System.out.println("Listening reconstructed: " + listening.size());
        Dictionary commonPart = new Product(reading, listening).result();
        System.out.println("Common part: " + commonPart.size());
        saveAs(commonPart, DictionaryFormatter.buildStandardFormatter(), DefaultConfig.understandingWhitelist, "reading_listening_merged");
    }

    private Dictionary reconstruct(Dictionary fullDictionary,
            IFeatureChooser exact,
            IFeatureChooser contain,
            String path) {
        return new DictionaryReconstructor(fullDictionary, exact, contain)
                .reconstruct(Util.loadFilesInDirectory(path));
    }
}
