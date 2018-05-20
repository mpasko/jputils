/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.mpasko.loadres.dictionaryFileLoader.LineSplitter;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class DictionaryFileLoader {

    public static List<Map.Entry<String, String>> parseAsSimpleMap(String source) {
        final LinkedList<Map.Entry<String, String>> extracted = new LinkedList<>();
        for (String line : source.split("\n")) {
            if (line.length() > 1) {
                extracted.addAll(LineSplitter.parseEntryOfManual(line));
            }
        }
        return extracted;
    }

    public Dictionary loadTripleDict(String filename) {
        String content = Filesystem.loadFile(filename);
        return loadTripleDictFromContent(content);
    }

    public Dictionary loadTripleDictFromFolder(String path) {
        String content = Filesystem.loadFilesInDirectory(path);
        return loadTripleDictFromContent(content);
    }

    private Dictionary loadTripleDictFromContent(String content) {
        Dictionary dict = new Dictionary();
        LineSplitter splitter = new LineSplitter();
        for (String line : content.split("\n")) {
            String[] split = splitter.splitLine(line);
            dict.put(split[0], split[1], split[2]);
        }
        return dict;
    }
}
