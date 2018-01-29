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
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.japanese.wordcomparison.OrComparer;
import org.mpasko.japanese.wordcomparison.SamePhonetic;
import org.mpasko.japanese.wordcomparison.StrictSynonimeComparer;
import org.mpasko.japanese.wordfilters.DuplicateFilter;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.loadres.dictionaryFileLoader.LineSplitter;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class ReconstructWritingFromManualDict {

    public static void main(String[] args) {
        final String manualPaths = DefaultConfig.workflowManualSources;
        final String outputPaths = DefaultConfig.workflowManualProcessed;
        Dictionary dict = new ReconstructWritingFromManualDict().reconstruct(manualPaths);
        dict = new DuplicateFilter(new OrComparer(
                new SamePhonetic(),
                new StrictSynonimeComparer()))
                .filter(dict);
        dict.write(outputPaths);
    }

    public Dictionary reconstruct(String inputs) {
        Dictionary sourceDict = new JmDictLoader().load(new JmDictLoader.DefaultFilter());
        List<Map.Entry<String, String>> manualDict = DictionaryFileLoader.parseAsSimpleMap(Util.loadFilesInDirectory(inputs));
        System.out.println(String.format("Lines of data discovered: %s", manualDict.size()));
        return reconstructFrom(manualDict, sourceDict);
    }

    public Dictionary reconstructFrom(List<Map.Entry<String, String>> manualDict, Dictionary sourceDict) {
        final Dictionary dictionary = new Dictionary();
        for (Map.Entry<String, String> entry : manualDict) {
            LinkedList<DictEntry> found;
            if (entry.getValue().isEmpty()) {
                found = sourceDict.findAllByReading(entry.getKey());
            } else {
                found = sourceDict.findPhoneticWithMeaning(entry.getKey(), entry.getValue());
            }
            dictionary.putAll(found);
        }
        return dictionary;
    }

}
