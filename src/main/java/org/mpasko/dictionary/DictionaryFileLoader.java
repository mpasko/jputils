/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import org.mpasko.loadres.dictionaryFileLoader.LineSplitter;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class DictionaryFileLoader {

    public Dictionary loadTripleDict(String filename) {
        String content = Util.loadFile(filename);
        return loadTripleDictFromContent(content);
    }

    public Dictionary loadTripleDictFromFolder(String path) {
        String content = Util.loadFilesInDirectory(path);
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
