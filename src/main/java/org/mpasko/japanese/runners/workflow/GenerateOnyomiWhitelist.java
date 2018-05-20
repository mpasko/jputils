/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.workflow;

import java.util.List;
import java.util.Map;
import org.mpasko.commons.analizers.ReadingDecomposer;
import org.mpasko.console.DefaultConfig;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class GenerateOnyomiWhitelist {

    public static void main(String[] args) {
        new GenerateOnyomiWhitelist().start();
    }

    public void start() {
        Dictionary whitelist = loadWhitelist();
        List<Map.Entry<String, String>> decomposed = ReadingDecomposer
                .initializeWithDefaultDict()
                .decompose(whitelist.getDict());
        Filesystem.saveFile(DefaultConfig.onyomiWhitelist, formatDecomposed(decomposed));
    }

    public Dictionary loadWhitelist() {
        DataSources data = new DataSources();
        Dictionary whitelist = data.listeningWhitelist();
        return whitelist;
    }

    public static String formatDecomposed(List<Map.Entry<String, String>> decomposed) {
        return decomposed
                .stream()
                .map(entry -> entry.getKey() + "-" + entry.getValue())
                .reduce("", (a, b) -> a + "\n" + b);
    }
}
