/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.mpasko.dictionary.Dictionary;

/**
 *
 * @author marcin
 */
public interface IFilter {

    Dictionary filter(Dictionary dict);
}
