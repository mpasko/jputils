/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.commons.Furiganiser;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.util.SimpleUtils;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class DictFromJishoSearch {
    public static void main(String args[]) {
        System.getProperties().setProperty("jdk.xml.entityExpansionLimit", "0");
        Dictionary full = new JmDictLoader().load(new JmDictLoader.DefaultFilter());
        Dictionary produced = new Dictionary();
        String trans = Util.loadFile("inputs/jisho_searches.txt");
        final Furiganiser furiganiser = new Furiganiser();
        StringBuilder log = new StringBuilder();
        for (String word : trans.split("\n")){
            String processed = word;
            processed = SimpleUtils.clear(processed);
            processed = furiganiser.furiganise(processed);
            try {
                processed = SimpleUtils.hexToUtf(processed.replaceAll("%", ""));
            } catch (Throwable ex) {
                //do nothing
            }
            log.append(word).append(" to: -> ").append(processed).append("\n");
            DictEntry entry = full.find(processed, processed);
            if (entry != null && entry.kanji.length() >= 2) {
                produced.put(entry);
            }
        }
        Util.saveFile("interproducts/jisho_srch_log.txt", log.toString());
        produced.write("dictionaries/from_jisho_searches.txt");
    }
}
