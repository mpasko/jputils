/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters;

import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class WritingChooser implements IFeatureChooser {

    @Override
    public String choose(DictEntry entry) {
        return entry.serializedReadings();
    }
}
