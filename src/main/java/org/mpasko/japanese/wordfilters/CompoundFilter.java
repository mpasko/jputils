/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;

/**
 *
 * @author marcin
 */
public class CompoundFilter implements IFilter{

    private List<IFilter> filters = new LinkedList<IFilter>();

    public CompoundFilter(IFilter grade) {
        filters.add(grade);
    }
    
    public CompoundFilter and(IFilter filter){
        filters.add(filter);
        return this;
    }

    @Override
    public Dictionary filter(Dictionary dict) {
        Dictionary partial = dict;
        for (IFilter filter : filters) {
            partial = filter.filter(partial);
        }
        return partial;
    }
    
}
