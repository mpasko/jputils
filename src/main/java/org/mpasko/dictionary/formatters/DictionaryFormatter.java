/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.formatters.complex.ListeningFormatter;
import org.mpasko.dictionary.formatters.complex.ReadingFormatter;
import org.mpasko.dictionary.formatters.complex.StandardFormatter;

/**
 *
 * @author marcin
 */
public class DictionaryFormatter {

    private final IFeatureChooser chooser;

    public DictionaryFormatter(IFeatureChooser chooser) {
        this.chooser = chooser;
    }

    public String format(Dictionary dict) {
        StringBuilder all = new StringBuilder();
        for (DictEntry item : dict.items()) {
            all.append(chooser.choose(item)).append("\n");
        }
        return all.toString();
    }

    public static DictionaryFormatter buildStandardFormatter() {
        return new DictionaryFormatter(
                new StandardFormatter());
    }

    public static DictionaryFormatter buildListeningFormatter() {
        return new DictionaryFormatter(
                new ListeningFormatter());
    }

    public static DictionaryFormatter buildReadingFormatter() {
        return new DictionaryFormatter(
                new ReadingFormatter());
    }
}
