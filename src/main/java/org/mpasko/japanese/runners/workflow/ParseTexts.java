/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.analizers.WordsExtractor;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.runners.parsers.ParseJishoOutputs;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.util.Util;
import org.mpasko.util.collectors.StringNewlineCollector;

/**
 *
 * @author marcin
 */
public class ParseTexts {

    private static Dictionary full_dict;

    public static void main(String argv[]) {
        processAllCategories();
    }

    private static void processAllCategories() {
        full_dict = JmDictLoader.loadDictionary();
        traverseDir("improved_workflow", "texts", ParseTexts::processCategory);
    }

    private static void processCategory(String base, String category) {
        traverseDir(base, category, ParseTexts::processAlbum);
    }

    private static void processAlbum(String base, String album) {
        String basePath = base + "/" + album;
        final List<String> subfiles = Util.getSubfiles(basePath);
        String mergedSongs = subfiles
                .stream()
                .map(path -> processSong(basePath, path))
                .collect(new StringNewlineCollector());
        StringBuilder content = new StringBuilder(subfiles.toString());
        content.append("\n").append(mergedSongs);
        Util.saveFile("texts/" + album + "_songs.txt", content.toString());
    }

    private static String processSong(String base, String song) {
        String filename = base + "/" + song;
        if (!filename.contains(".eng.txt")) {
            final String title = "[" + song + "]";
            String jap = Util.loadFile(filename);
            String eng = Util.loadFile(filename.replaceAll(".txt", ".eng.txt"));
            List<String> words = findWords(jap, full_dict);
            Dictionary filtered = ParseJishoOutputs.findAndFilterItemsFromDictionary(words, jap, full_dict);
            return new StringBuilder(title).append("\n")
                    .append(jap).append("\n")
                    .append(eng).append("\n")
                    .append(filtered.toString())
                    .toString();
        }
        return "";
    }

    private static List<String> findWords(String jap, Dictionary dict) {
        return new WordsExtractor(dict)
                .extractFromText(jap)
                .stream()
                .map(entry -> entry.kanji)
                .collect(Collectors.toList());
    }

    private static void traverseDir(String base, String subdir, Subprocessor subprocessor) {
        String basePath = base + "/" + subdir;
        Util.getSubdirectories(basePath)
                .stream()
                .forEach(path -> subprocessor.apply(basePath, path));
    }

    @FunctionalInterface
    interface Subprocessor {

        public void apply(String base, String item);
    }
}
