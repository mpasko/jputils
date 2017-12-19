/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.List;
import org.mpasko.commons.DictEntry;
import org.mpasko.japanese.wordcomparison.IWordComparer;

/**
 *
 * @author marcin
 */
public class ItemExistsInDictionary {

    public boolean exists(DictEntry item, List<DictEntry> dict, IWordComparer criteria) {
        return dict
                .stream()
                .anyMatch(previous -> criteria.areSimillar(previous, item));
    }
}
