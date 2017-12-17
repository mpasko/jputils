/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordcomparison;

import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public interface IWordComparer {

    boolean areSimillar(DictEntry entry1, DictEntry entry2);
}
