/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.parseTexts;

import java.util.HashMap;
import org.mpasko.commons.Furiganiser;

/**
 *
 * @author marcin
 */
public class GrammarStemmer {

    private HashMap<String, String> map = new HashMap<String, String>();

    public GrammarStemmer() {
        initialize();
    }

    private void initialize() {
        register("んたい", "");
        register("たい", "る");
        register("でした", "");
        register("です", "");
        register("せん", "す");
        register("きます", "く");
        register("ります", "る");
        register("き", "く");
        register("っこう", "く");
        register("って", "る");
        register("った", "る");
        register("らる", "る");
        register("ある", "る");
        register("かる", "く");
        register("れる", "る");
        register("える", "る");
        register("ける", "く");
        transformIntoU("ま", "み", "め", "も", "か", "き", "け", "こ");
        transformIntoU("さ", "し", "せ", "そ", "ら", "り", "れ", "ろ");
    }

    private void transformIntoU(String... sylabes) {
        Furiganiser furiganizer = new Furiganiser();
        for (String sylabe : sylabes) {
            String romanized = furiganizer.romanize(sylabe);
            String transformed = romanized.replaceAll("[aeio]", "u");
            register(sylabe, furiganizer.furiganise(transformed));
        }
    }

    public String stem(String word) {
        for (String key : map.keySet()) {
            if (word.endsWith(key)) {
                word = word.replaceAll(key, map.get(key));
            }
        }
        return word;
    }

    private void register(String form, String dictForm) {
        map.put(form, dictForm);
    }
}
