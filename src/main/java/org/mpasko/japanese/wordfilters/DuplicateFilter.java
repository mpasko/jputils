/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.LinkedList;
import org.mpasko.commons.DictEntry;
import org.mpasko.japanese.wordcomparison.IWordComparer;

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
        boolean match = !alreadyExists(entry);
        alreadyProcessed.add(entry);
        return match;
    }

    private boolean alreadyExists(DictEntry entry) {
        return alreadyProcessed
                .stream()
                .anyMatch(previous -> comparer.areSimillar(previous, entry));
    }

}
