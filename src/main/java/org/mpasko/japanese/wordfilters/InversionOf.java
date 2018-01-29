/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class InversionOf extends GenericFilter {

    private final GenericFilter inverted;

    public InversionOf(GenericFilter source) {
        this.inverted = source;
    }

    @Override
    public boolean itemMatches(DictEntry entry) {
        return !inverted.itemMatches(entry);
    }

}
