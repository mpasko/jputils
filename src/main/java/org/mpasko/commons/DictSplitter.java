/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author marcin
 */
public class DictSplitter {
    public List<Dictionary> split(Dictionary in, int num) {
        int parts = (int)Math.ceil(((float)in.size())/num);
        int sizes = in.size()/parts+1;
        LinkedList<Dictionary> out = new LinkedList<Dictionary>();
        out.add(new Dictionary());
        for(DictEntry en : in.items()) {
            if (out.getLast().size()>=sizes) {
                out.addLast(new Dictionary());
            }
            out.getLast().put(en);
        }
        return out;
    }
}
