/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author marcin
 */
public class StringUtils {

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return clear(str1).equalsIgnoreCase(clear(str2));
    }

    public static double indexOfIgnoreCase(List<String> list, String item) {
        int index = 0;
        for (String listitem : list) {
            if (listitem.equalsIgnoreCase(item)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    public static boolean containsIgnoreCase(List<String> list, String item) {
        return indexOfIgnoreCase(list, item) >= 0;
    }

    public static String clear(String string) {
        String charsRemoved = string.replaceAll("-", "").replaceAll("\n", "").replaceAll("\r", "");
        String comasUnified = charsRemoved.replaceAll(";", ",").replaceAll(", ", ",");
        return comasUnified.trim().toLowerCase();
    }

    public static boolean containEachOther(String item1, String item2) {
        return contains(item1, item2) || contains(item2, item1);
    }

    private static boolean contains(String item1, String item2) {
        return clear(item1).contains(clear(item2));
    }

    public static int lengthDifference(String item1, String item2) {
        return Math.abs(item1.length() - item2.length());
    }

    public static String cutLast(String path, String element) {
        if (path.endsWith(element)) {
            return path.substring(0, path.length() - element.length());
        } else {
            return path;
        }
    }

    public static String cutFirst(String path, String element) {
        if (path.startsWith(element)) {
            return path.substring(element.length(), path.length());
        } else {
            return path;
        }
    }

    public static String joinPath(String... nodes) {
        String result = Arrays
                .stream(nodes)
                .map(path -> path.replace("/", "\\"))
                .map(path -> cutFirst(path, "\\"))
                .map(path -> cutLast(path, "\\"))
                .collect(Collectors.joining("\\"));
        return result;
    }

    public static String lastSegment(String source, String delimiter) {
        String[] split = source.split(delimiter);
        return split[split.length - 1];
    }
}
