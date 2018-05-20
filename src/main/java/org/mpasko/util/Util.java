/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcin
 */
public class Util {

    public static void isNull(Object obj, String name) {
        if (obj == null) {
            System.out.println(name + " is null!");
        }
    }

    public static <T> List<T> singleElemList(T item) {
        List<T> list = new LinkedList<T>();
        list.add(item);
        return list;
    }

    public static List<String> strings(String... elem) {
        List<String> elems1 = new LinkedList<String>();
        elems1.addAll(Arrays.asList(elem));
        return elems1;
    }

    public static String stringifyList(List<String> kunomi) {
        StringBuilder b = new StringBuilder();
        for (String kun : kunomi) {
            if (!kun.equals(kunomi.get(0))) {
                b.append(",");
            }
            b.append(kun);
        }
        return b.toString();
    }

    public static void binaryToStream(String content, OutputStream fos) {
        try {
            fos.write(content.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    public static String negatedComparator(final String comparator) {
        String neg;
        if (comparator.equals("<")) {
            neg = ">=";
        } else if (comparator.equals(">")) {
            neg = "<=";
        } else if (comparator.equals(">=")) {
            neg = "<";
        } else if (comparator.equals("<=")) {
            neg = ">";
        } else if (comparator.equals("!=") || comparator.equals("<>")) {
            neg = "=";
        } else {
            neg = "<>";
        }
        return neg;
    }

    public static Map<String, Object> lowercaseKeys(Map<String, Object> event) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        for (Entry<String, Object> entry : event.entrySet()) {
            //result.put(entry.getKey(), entry.getValue());
            result.put(entry.getKey().toLowerCase(Locale.getDefault()), entry.getValue());
        }
        return result;
    }

    public static String formatEventByKeyParams(List<String> keys, Map<String, Object> event) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        int index = 0;
        for (String key : keys) {
            result.append(key).append(": ");
            result.append(event.get(key));
            if (index < keys.size() - 1) {
                result.append(", ");
            }
        }
        result.append("}");
        String string = result.toString();
        return string;
    }

    public static String formatEventByKeyParams(Map<String, Object> event) {
        return formatEventByKeyParams(new ArrayList<String>(event.keySet()), event);
    }
}
