/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;

import java.util.List;

/**
 *
 * @author marcin
 */
public interface IFilter {

    Dictionary filter(Dictionary dict);
    List<DictEntry> filter(List<DictEntry> dict);
}
