/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.Map;
import java.util.stream.Collectors;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.formatters.DictionaryFormatter;
import org.mpasko.dictionary.operations.Product;
import org.mpasko.japanese.wordfilters.DuplicateFilter;
import org.mpasko.japanese.wordfilters.OnlyKanjiFilter;
import org.mpasko.japanese.wordfilters.OnyomiSpeculationFilter;
import org.mpasko.japanese.wordfilters.wordsplitter.SplittingFilter;
import org.mpasko.util.Filesystem;
import org.mpasko.util.StringUtils;

/**
 *
 * @author marcin
 */
public class ProcessEverything {

    DataSources data = new DataSources();

    public static void main(String[] args) {
        new ProcessEverything().start();
    }

    public void start() {
        generateListeningFromSelectedSources();
        generateReadingFromSelectedSources();
        mergeReadingAndListening();
    }

    private Map<String, String> directoriesAsSelectedSources() {
        return DataSources.getGlobalSourceList()
                .stream()
                .collect(Collectors.toMap(dir -> dir, dir -> DefaultConfig.globalSources + "\\" + dir + "\\"));
    }

    private void generateReadingFromSelectedSources() {
        final DictionaryFormatter formatter = DictionaryFormatter.buildReadingFormatter();
        directoriesAsSelectedSources()
                .entrySet()
                .stream()
                .forEach((entry) -> generateWritingFromSelectedSource(entry.getKey(), entry.getValue(), formatter, DefaultConfig.readingSources));
    }

    private void generateListeningFromSelectedSources() {
        final DictionaryFormatter formatter = DictionaryFormatter.buildListeningFormatter();
        generateFromSelectedSourcesUsingFormatter(formatter, DefaultConfig.listeningSources);
    }

    private void generateFromSelectedSourcesUsingFormatter(final DictionaryFormatter formatter, String outputDirectory) {
        directoriesAsSelectedSources()
                .entrySet()
                .stream()
                .forEach(entry
                        -> generateListeningFromSelectedSource(entry.getKey(), entry.getValue(), formatter, outputDirectory));
    }

    private void generateListeningFromSelectedSource(String category, String path, DictionaryFormatter formatter, String outputDirectory) {
        Dictionary dict = new DictionaryFileLoader().loadTripleDictFromFolder(path);
        dict = applyRequiredFilters(dict);
        saveAs(dict, formatter, outputDirectory, category);
    }

    private void generateWritingFromSelectedSource(String category, String path, DictionaryFormatter formatter, String outputDirectory) {
        Dictionary dict = new DictionaryFileLoader().loadTripleDictFromFolder(path);
        dict = applyRequiredFilters(dict);
        dict = OnyomiSpeculationFilter.buildStandardFilter().filter(dict);
        saveAs(dict, formatter, outputDirectory, category);
    }

    private Dictionary applyRequiredFilters(Dictionary dict) {
        Dictionary new_dict = dict;
        new_dict = new SplittingFilter(data.globalDictionary(), "", "")
                .filter(new_dict);
        DuplicateFilter duplicateFilter = DuplicateFilter.outputDictionaryDuplicateFilter();
        new_dict = duplicateFilter.filter(new_dict);
        new_dict = OnlyKanjiFilter.katakanaFilter().filter(new_dict);
        return new_dict;
    }

    private void saveAs(Dictionary dict, DictionaryFormatter formatter, String outputDirectory, String category) {
        dict = DuplicateFilter.outputDictionaryDuplicateFilter().filter(dict);
        String content = formatter.format(dict);
        final String filename = String.format("%s.txt", StringUtils.joinPath(outputDirectory, category));
        Filesystem.saveFile(filename, content);
    }

    private void mergeReadingAndListening() {
        Dictionary reading = data.readingWhitelist();
        Dictionary listening = data.listeningWhitelist();
        Dictionary commonPart = new Product(reading, listening).result();
        System.out.println("Common part: " + commonPart.size());
        saveAs(commonPart, DictionaryFormatter.buildStandardFormatter(), DefaultConfig.understandingWhitelist, "reading_listening_merged");
    }
}
