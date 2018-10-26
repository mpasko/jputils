/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.parseTexts.splitters;

import java.util.List;
import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public interface ISplitter {

    List<DictEntry> split(DictEntry word);
}
