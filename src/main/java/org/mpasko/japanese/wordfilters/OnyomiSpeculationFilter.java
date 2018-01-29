/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.List;
import java.util.Map;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.analizers.ReadingDecomposer;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.DictionaryFileLoader;
import org.mpasko.dictionary.MultipleIndexer;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class OnyomiSpeculationFilter extends GenericFilter {

    //List<MapEntry<String, String>>
    MultipleIndexer<String> index = new MultipleIndexer<>();
    ReadingDecomposer decomposer;

    public OnyomiSpeculationFilter(List<Map.Entry<String, String>> onyomiWhitelist, ReadingDecomposer decomposer) {
        this.decomposer = decomposer;
        onyomiWhitelist.stream().forEach(entry -> index.put(entry.getKey(), entry.getValue()));
    }

    public static OnyomiSpeculationFilter initializeDefault() {
        final String fileContent = Util.loadFile(DefaultConfig.onyomiWhitelist);
        final List<Map.Entry<String, String>> onyomiWhitelist = DictionaryFileLoader.parseAsSimpleMap(fileContent);
        final ReadingDecomposer decomposer = ReadingDecomposer.initializeWithDefaultDict();
        return new OnyomiSpeculationFilter(onyomiWhitelist, decomposer);
    }

    @Override
    public boolean itemMatches(DictEntry entry) {
        List<Map.Entry<String, String>> decomposition = decomposer.decompose(entry);
        return decomposition.stream()
                .map(step -> getMatchingReadingVariant(step.getKey(), step.getValue()))
                .allMatch(readingPart -> !readingPart.isEmpty());
    }

    private String getMatchingReadingVariant(String kanji, String total) {
        if (total == null) {
            return "";
        }
        return index.getAll(kanji)
                .stream()
                .filter(reading -> total.contains(reading))
                .map(this::debug)
                .findAny()
                .orElse("");
    }

    private String debug(String item) {
        System.out.println("Debuging: " + item);
        return item;
    }

}
