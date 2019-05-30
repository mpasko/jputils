/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

/**
 *
 * @author marcin
 */
public class NumericUtil {

    public static int parseIntegerDefault(String param, int defaultValue) {
        String val = param;
        val = val.replaceAll("\"", "");
        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }
}
