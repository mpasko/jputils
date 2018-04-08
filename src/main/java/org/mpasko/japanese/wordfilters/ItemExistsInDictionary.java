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
public class ItemExistsInDictionary extends GenericFilter {

    private final List<DictEntry> dict;
    private final IWordComparer criteria;

    public ItemExistsInDictionary(List<DictEntry> dict, IWordComparer criteria) {
        this.dict = dict;
        this.criteria = criteria;
    }

    public ItemExistsInDictionary(List<DictEntry> dict) {
        this(dict, DuplicateFilter.standardDuplicateRecognition());
    }

    @Override
    public boolean itemMatches(DictEntry entry) {
        return dict
                .stream()
                .anyMatch(previous -> criteria.areSimillar(previous, entry));
    }
}
