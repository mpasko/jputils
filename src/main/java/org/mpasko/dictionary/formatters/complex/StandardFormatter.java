/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters.complex;

import org.mpasko.dictionary.formatters.KanjiWritingChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;

/**
 *
 * @author marcin
 */
public class StandardFormatter extends KeyValueChooser {

    public StandardFormatter() {
        super(new KanjiWritingChooser(), new MeaningChooser());
    }

}
