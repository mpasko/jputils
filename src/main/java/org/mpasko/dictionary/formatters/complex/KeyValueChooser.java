/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters.complex;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.formatters.IFeatureChooser;

/**
 *
 * @author marcin
 */
public class KeyValueChooser implements IFeatureChooser {

    private final IFeatureChooser key;
    private final IFeatureChooser value;

    public KeyValueChooser(IFeatureChooser key, IFeatureChooser value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String choose(DictEntry entry) {
        return key.choose(entry) + "-" + value.choose(entry);
    }

}
