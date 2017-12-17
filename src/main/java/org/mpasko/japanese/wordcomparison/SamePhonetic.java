/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordcomparison;

import org.mpasko.commons.DictEntry;
import org.mpasko.util.StringUtils;

/**
 *
 * @author marcin
 */
public class SamePhonetic implements IWordComparer {

    @Override
    public boolean areSimillar(DictEntry entry1, DictEntry entry2) {
        return StringUtils.equalsIgnoreCase(entry1.writing, entry2.writing);
    }

}
