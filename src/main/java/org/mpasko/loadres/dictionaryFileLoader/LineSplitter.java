/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.loadres.dictionaryFileLoader;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;
import org.mpasko.util.StringUtils;

/**
 *
 * @author marcin
 */
public class LineSplitter {

    public String[] splitLine(String line) {
        String[] byDash = line.split("-");
        /* */
        if (byDash.length == 0) {
            return new String[]{"", "", ""};
        }
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

    public static LinkedList<Map.Entry<String, String>> parseEntryOfManual(String source) {
        final LinkedList<Map.Entry<String, String>> extracted = new LinkedList<>();
        final String[] split = source.split("-");
        String key = split[0];
        if (split.length == 1) {
            extracted.push(new AbstractMap.SimpleEntry<>(key.trim(), ""));
        } else {
            String values = split[1];
            for (String value : values.split(",")) {
                extracted.push(new AbstractMap.SimpleEntry<>(key.trim(), value.trim()));
            }
        }
        return extracted;
    }
}
