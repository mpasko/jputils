/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.formatters.DictionaryFormatter;
import org.mpasko.japanese.wordfilters.DuplicateFilter;
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

    public void start() {
        generateListeningFromSelectedSources();
        generateReadingFromSelectedSources();
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
        dict = DuplicateFilter.outputDictionaryDuplicateFilter().filter(dict);
        String content = formatter.format(dict);
        final String filename = String.format("%s.txt", StringUtils.joinPath(outputDirectory, category));
        Util.saveFile(filename, content);
    }
}
