/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters.complex;

import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.WritingChooser;

/**
 *
 * @author marcin
 */
public class ListeningFormatter extends KeyValueChooser {

    public ListeningFormatter() {
        super(new WritingChooser(), new MeaningChooser());
    }

}
