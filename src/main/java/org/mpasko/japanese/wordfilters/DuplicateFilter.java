/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.LinkedList;
import org.mpasko.commons.DictEntry;
import org.mpasko.japanese.wordcomparison.AndComparer;
import org.mpasko.japanese.wordcomparison.GrammaticalComparer;
import org.mpasko.japanese.wordcomparison.IWordComparer;
import org.mpasko.japanese.wordcomparison.SynonimeComparer;

/**
 *
 * @author marcin
 */
public class DuplicateFilter extends GenericFilter {

    private LinkedList<DictEntry> alreadyProcessed = new LinkedList<>();
    private final IWordComparer comparer;

    public DuplicateFilter(IWordComparer comparer) {
        this.comparer = comparer;
    }

    @Override
    public boolean itemMatches(DictEntry entry) {
        boolean match = !new ItemExistsInDictionary().exists(entry, alreadyProcessed, comparer);
        alreadyProcessed.add(entry);
        return match;
    }

    public static DuplicateFilter outputDictionaryDuplicateFilter() {
        final AndComparer duplicateRecognition = new AndComparer(
                new GrammaticalComparer(),
                new SynonimeComparer());
        final DuplicateFilter duplicateFilter = new DuplicateFilter(duplicateRecognition);
        return duplicateFilter;
    }

}
