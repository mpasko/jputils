/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.runonce;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.mpasko.commons.DictEntry;
import org.mpasko.configuration.DefaultPaths;
import org.mpasko.dictionary.operations.MergeStrict;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.japanese.wordcomparison.OrComparer;
import org.mpasko.japanese.wordcomparison.SamePhonetic;
import org.mpasko.japanese.wordcomparison.StrictSynonimeComparer;
import org.mpasko.japanese.wordfilters.DuplicateFilter;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class ReconstructWritingFromManualDict {

    public static void main(String[] args) {
        final String manualPaths = DefaultPaths.workflowManualSources;
        final String outputPaths = DefaultPaths.workflowManualProcessed;
        Dictionary dict = new ReconstructWritingFromManualDict().reconstruct(manualPaths);
        dict = new DuplicateFilter(new OrComparer(
                new SamePhonetic(),
                new StrictSynonimeComparer()))
                .filter(dict);
        dict.write(outputPaths);
    }

    public Dictionary reconstruct(String inputs) {
        Dictionary sourceDict = new JmDictLoader().load(new JmDictLoader.DefaultFilter());
        List<Map.Entry<String, String>> manualDict = DictionaryFileLoader.parseAsSimpleMap(new Filesystem().loadFilesInDirectory(inputs));
        System.out.println(String.format("Lines of data discovered: %s", manualDict.size()));
        return reconstructFrom(manualDict, sourceDict);
    }

    public Dictionary reconstructFrom(List<Map.Entry<String, String>> manualDict, Dictionary sourceDict) {
        final Dictionary dictionary = new Dictionary();
        final MergeStrict merger = new MergeStrict();
        for (Map.Entry<String, String> entry : manualDict) {
            LinkedList<DictEntry> found;
            if (entry.getValue().isEmpty()) {
                found = sourceDict.findAllByReading(entry.getKey());
            } else {
                found = sourceDict.findPhoneticWithMeaning(entry.getKey(), entry.getValue());
            }
            merger.mergeDictionaries(dictionary, found);
        }
        return dictionary;
    }

}
