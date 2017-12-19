/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

/**
 *
 * @author marcin
 */
public class FormatterUtil {

    public static String alignString(int desiredLength, String textToFill, final String text) {
        String prefix = "";
        for (int i = 0; i < desiredLength - text.length(); ++i) {
            prefix = prefix.concat(textToFill);
        }
        return prefix.concat(text);
    }

    public static String alignStringForward(int desiredLength, String textToFill, final String text) {
        String prefix = text;
        for (int i = 0; i < desiredLength - text.length(); ++i) {
            prefix = prefix.concat(textToFill);
        }
        return prefix;
    }
}
