/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners;

import java.util.Arrays;
import java.util.List;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.japanese.kanjifilters.LoaderFilterByText;
import org.mpasko.loadres.KanjiDictLoader;
import org.mpasko.loadres.KanjiDictLoader.Filter;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class KanjiListFromText {
    public static List<String> chinese_songs = Arrays.asList("say_sth", 
                                                    "1ren1pu",
                                                    "all_of",
                                                    "fox",
                                                    "lemon",
                                                    "dienwa",
                                                    "that_way",
                                                    "xingqinguu",
                                                    "from_taipei_to_peijing",
                                                    "queen",
                                                    "she_everywhere");
    public static void main(String args[]) {
        for (String name : chinese_songs) {
            songIntoKanjiDic(name);
        }
    }

    public static void songIntoKanjiDic(String name) {
        KanjiDictionary dictionary = loadChineseSong(name);
        String output = dictionary.format();
        new Filesystem().saveFile(String.format("texts\\chinese_songs\\%s.txt", name), output);
    }

    public static KanjiDictionary loadChineseSong(String name) {
        String text = new Filesystem().loadFile(String.format("inputs\\chinese_songs\\%s.txt", name));
        Filter filter = LoaderFilterByText.scanned(text).getInstance();
        KanjiDictionary dictionary = new KanjiDictLoader().load(filter);
        return dictionary;
    }
}
