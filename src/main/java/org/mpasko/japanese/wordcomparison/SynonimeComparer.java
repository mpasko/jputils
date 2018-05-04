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
public class SynonimeComparer implements IWordComparer {

    @Override
    public boolean areSimillar(DictEntry entry1, DictEntry entry2) {
        if (entry1.english.equalsIgnoreCase(entry2.english)) {
            return true;
        }
        String[] list1 = makeListFrom(entry1);
        String[] list2 = makeListFrom(entry2);
        /*
        if (list1.length > 3 || list2.length > 3) {
            return false;
        }
         */
        for (String item1 : list1) {
            if (item1.length() >= 4) {
                for (String item2 : list2) {
                    if (StringUtils.lengthDifference(item1, item2) <= 3
                            && StringUtils.containEachOther(item1, item2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String[] makeListFrom(DictEntry entry1) {
        String operating1 = entry1.english.replaceAll(",", " ");
        String[] list1 = operating1.split(" ");
        return list1;
    }
}
