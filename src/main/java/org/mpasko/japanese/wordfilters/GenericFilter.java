/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;

/**
 *
 * @author marcin
 */
public abstract class GenericFilter implements IFilter {
    private static final Logger LOGGER = LogManager.getLogger(GenericFilter.class.getName());

    public static IFilter buildStandardFilter() {
        final GradeFilter grade = GradeFilter.build(3, 8, true);
        final KnownWordsFilter known = KnownWordsFilter.build();
        return new CompoundFilter(grade).and(known);
    }

    public Dictionary filter(Dictionary dict) {
        Dictionary filtered = new Dictionary();
        for (DictEntry item : dict.items()) {
            if (itemMatches(item)) {
                filtered.put(item);
                String message = String.format("Accepted item: %s", item.serializedKeywords());
                LOGGER.info(message);
            }
        }
        return filtered;
    }

    public abstract boolean itemMatches(DictEntry entry);
}
