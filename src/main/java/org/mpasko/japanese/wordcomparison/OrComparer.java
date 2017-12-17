/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordcomparison;

import java.util.Arrays;
import java.util.LinkedList;
import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class OrComparer implements IWordComparer {

    private final LinkedList<IWordComparer> comparers;

    public OrComparer(IWordComparer... comparers) {
        this.comparers = new LinkedList<IWordComparer>(Arrays.asList(comparers));
    }

    @Override
    public boolean areSimillar(DictEntry entry1, DictEntry entry2) {
        return comparers
                .stream()
                .anyMatch(comparer -> comparer.areSimillar(entry1, entry2));
    }

}
