/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters;

import org.mpasko.commons.DictEntry;
import org.mpasko.commons.Furiganiser;

/**
 *
 * @author marcin
 */
public class RomajiWritingChooser implements IFeatureChooser {

    @Override
    public String choose(DictEntry entry) {
        return new Furiganiser().romanize(entry.serializedReadings());
    }

}
