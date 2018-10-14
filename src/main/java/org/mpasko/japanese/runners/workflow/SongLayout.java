/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.analizers.WordsExtractor;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.editor.Asset;
import org.mpasko.japanese.wordfilters.compound.ExtractorFilter;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class SongLayout {

    public String process(String song, String filename, String category, Dictionary full_dict) {
        String jap = new Filesystem().loadFile(filename);
        final String dictionaryStringified = findAndFilterWords(jap, full_dict);
        new Filesystem().saveFile(String.format("./%s/%s/%s", DefaultConfig.globalSources, category, song), dictionaryStringified);
        return dryProcess(song, filename, full_dict);
    }

    public String dryProcess(String song, String filename, Dictionary full_dict) {
        return generateChunked(song, Asset.load(filename), full_dict);
    }

    private static List<String> findWords(String jap, Dictionary dict) {
        return new WordsExtractor(dict)
                .extractFromText(jap)
                .stream()
                .map(entry -> entry.kanji)
                .collect(Collectors.toList());
    }

    private String findAndFilterWords(String sourceText, Dictionary full_dict) {
        List<String> words = findWords(sourceText, full_dict);
        Dictionary filtered = ExtractorFilter.findAndFilterItemsFromDictionary(words, sourceText, full_dict);
        return filtered.toString();
    }

    private String generateChunked(String title, Asset source, Dictionary global_dict) {
        StringBuilder result = new StringBuilder("[" + title + "]").append("\n");
        for (String chunk : splitEvenly(source.japanese)) {
            result.append(formatChunk(chunk, global_dict)).append("\n");
        }
        result.append(source.english).append("\n");
        return result.toString();
    }

    private String formatChunk(String sourceText, Dictionary global_dict) {
        final String dictionaryStringified = findAndFilterWords(sourceText, global_dict);
        return new StringBuilder(dictionaryStringified).append("\n")
                .append(sourceText)
                .toString();
    }

    private List<String> splitEvenly(String songText) {
        final LinkedList<String> result = new LinkedList<>();
        String[] split = songText.split("\n|。");
        StringBuilder currentChunk = new StringBuilder();
        for (int index = 0; index < split.length; ++index) {
            currentChunk.append(split[index]).append("\n");
            if (currentChunk.length() > calculateChunkSize(songText)) {
                result.add(currentChunk.toString());
                currentChunk = new StringBuilder();
            }
        }
        result.add(currentChunk.toString());
        return result;
    }

    private int calculateChunkSize(String songText) {
        int total = songText.length();
        if (total < 500) {
            return total;
        } else {
            int chunksNumber = total / 200;
            return total / chunksNumber;
        }
    }
}
