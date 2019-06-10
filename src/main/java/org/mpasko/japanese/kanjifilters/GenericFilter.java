/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.kanjifilters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.KanjiEntry;


/**
 *
 * @author marcin
 */
public abstract class GenericFilter {

    private static final Logger LOGGER = LogManager.getLogger(GenericFilter.class.getName());

    public KanjiDictionary filter(KanjiDictionary dict) {
        KanjiDictionary filtered = new KanjiDictionary();
        for (KanjiEntry item : dict.getItems()) {
            if (itemMatches(item)) {
                filtered.put(item);
                String message = String.format("Accepted item: %s", item.character);
                LOGGER.info(message);
            }
        }
        return filtered;
    }
    
    public abstract boolean itemMatches(KanjiEntry entry);
}
