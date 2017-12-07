/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.LinkedList;
import org.mpasko.commons.Classifier;

/**
 *
 * @author marcin
 */
public class LangUtils {
    
    
    public static String getOnlyPreInfix(String kana) {
        char[] ckana = kana.toCharArray();
        String stripped = stripSuffix(ckana, 0).toString();
        return kana.replace(stripped, "");
    }

    public static StringBuilder stripSuffix(char[] ckana, int k) {
        LinkedList<Character> queue = new LinkedList<Character>();
        for (int end = ckana.length-1; end>=k; --end) {
            if (Classifier.classify(ckana[end]).containsKanji()) {
                break;
            } else {
                queue.addFirst(ckana[end]);
            }
        }
        StringBuilder out = new StringBuilder();
        for (Character c : queue) {
            out.append(c);
        }
        return out;
    }
}
