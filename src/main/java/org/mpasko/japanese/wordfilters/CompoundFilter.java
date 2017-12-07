/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class CompoundFilter extends GenericFilter{

    private List<GenericFilter> filters = new LinkedList<GenericFilter>();

    public CompoundFilter(GenericFilter grade) {
        filters.add(grade);
    }
    
    public CompoundFilter and(GenericFilter filter){
        filters.add(filter);
        return this;
    }
    
    @Override
    public boolean itemMatches(DictEntry entry) {
        boolean matches = true;
        for (GenericFilter filter : filters) {
            matches &= filter.itemMatches(entry);
        }
        return matches;
    }
    
}
