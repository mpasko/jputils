/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import java.util.LinkedList;
import java.util.List;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class KanjiEntry {
    public String character;
    public String meaning;
    public List<String> onyomi = new LinkedList<String>();
    public List<String> kunomi = new LinkedList<String>();
    public List<String> pinyin = new LinkedList<String>();

    public String formatAnswer() {
        StringBuilder b = new StringBuilder();
        b.append(Util.stringifyList(KunSimplifier.simplifyKunReading(kunomi))).append("/");
        b.append(Util.stringifyList(onyomi)).append("/");
        b.append(Util.stringifyList(pinyin)).append(" -");
        b.append(meaning);
        return b.toString();
    }
}
