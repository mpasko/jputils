/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.mpasko.commons.DictEntry;
import sun.reflect.generics.factory.GenericsFactory;

/**
 *
 * @author marcin
 */
public class LengthFilter extends GenericFilter{
    private int desiredFrom;
    private int desiredTo;

    public LengthFilter(int desired) {
        this.desiredFrom = desired;
        this.desiredTo = desired;
    }
    
    public LengthFilter(int from, int to) {
        this.desiredFrom = from;
        this.desiredTo = to;
    }
    
    @Override
    public boolean itemMatches(DictEntry entry) {
        return (entry.kanji.length() >= desiredFrom) && (entry.kanji.length() <= desiredTo);
    }
    
}
