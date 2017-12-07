/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.parsers;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.japanese.wordfilters.OnlyKanjiFilter;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.loadres.JmDictLoader.DefaultFilter;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class JMDictsByField {
    public static void main(String args[]) {
        generateDictForField("math");
        generateDictForField("anat");
        generateDictForField("chem");
        generateDictForField("comp");
        generateDictForField("geom");
        generateDictForField("med");
        generateDictForField("physics");
        generateDictForField("sports");
        generateDictForField("MA");
        generateDictForField("engr");
    }

    private static void generateDictForField(String field) {
        JmDictLoader loader = new JmDictLoader();
        DefaultFilter filter = new JmDictLoader.DefaultFilter().addFieldFilter(field);
        Dictionary dict = loader.load(filter);
        dict = new OnlyKanjiFilter().filter(dict);
        dict.write(String.format("dictionaries/fields/%s.txt", field));
    }
}
