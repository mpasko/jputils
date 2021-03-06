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
public class SameKanji implements IWordComparer {

    @Override
    public boolean areSimillar(DictEntry entry1, DictEntry entry2) {
        boolean stright = StringUtils.equalsIgnoreCase(entry1.serializedKeywords(), entry2.serializedKeywords());
        boolean skipKanji1 = StringUtils.equalsIgnoreCase(entry1.serializedReadings(), entry2.serializedKeywords());
        boolean skipKanji2 = StringUtils.equalsIgnoreCase(entry1.serializedKeywords(), entry2.serializedReadings());
        return stright || skipKanji1 || skipKanji2;
    }

}
