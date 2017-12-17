/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.loadres.dictionaryFileLoader;

import org.mpasko.util.SimpleUtils;
import org.mpasko.util.StringUtils;

/**
 *
 * @author marcin
 */
public class LineSplitter {

    public String[] splitLine(String line) {
        String[] byDash = line.split("-");
        /* */
        String en = StringUtils.clear(byDash[byDash.length - 1]);
        String jp = assembleAgain(byDash, 0, byDash.length - 2, " ");
        String[] bySpace = jp.split(" ");
        String kanji = StringUtils.clear(bySpace[0]);
        String rest = StringUtils.clear(assembleAgain(bySpace, 1, bySpace.length - 1, " "));
        /* */
        return new String[]{kanji, rest, en};
    }

    private static String assembleAgain(String[] byDash, int from, int to, String separator) {
        StringBuilder result = new StringBuilder();
        for (int i = from; i <= to; ++i) {
            result.append(byDash[i]);
            if (separator != null && i < to) {
                result.append(separator);
            }
        }
        return result.toString();
    }
}
