/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters.complex;

import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;

/**
 *
 * @author marcin
 */
public class ReadingFormatter extends KeyValueChooser {

    public ReadingFormatter() {
        super(new KanjiChooser(), new RomajiWritingChooser());
    }

}
