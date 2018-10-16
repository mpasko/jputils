/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.List;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.util.Filesystem;
import org.mpasko.util.StringUtils;
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
        final List<String> subfiles = new Filesystem().getSubfiles(basePath);
        String mergedSongs = subfiles
                .stream()
                .map(path -> processSong(basePath, path))
                .collect(new StringNewlineCollector());
        StringBuilder content = new StringBuilder(subfiles.toString());
        content.append("\n").append(mergedSongs);
        new Filesystem().saveFile("texts/" + album + "_songs.txt", content.toString());
    }

    private static String processSong(String base, String song) {
        String category = StringUtils.lastSegment(base, "/");
        String filename = base + "/" + song;
        if (!filename.contains(".eng.txt")) {
            return new SongLayout().process(song, filename, category, full_dict);
        }
        return "";
    }

    public static void traverseDir(String base, String subdir, Subprocessor subprocessor) {
        String basePath = base + "/" + subdir;
        new Filesystem().getSubdirectories(basePath)
                .stream()
                .forEach(path -> subprocessor.apply(basePath, path));
    }

    @FunctionalInterface
    interface Subprocessor {

        public void apply(String base, String item);
    }
}
