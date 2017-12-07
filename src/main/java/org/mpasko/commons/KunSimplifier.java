/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author marcin
 */
public class KunSimplifier {

    public static boolean isNewKun(LinkedList<String> accepted, String kun) {
        for (String acc : accepted) {
            if (compareKun(acc, kun)) {
                return false;
            }
            if (compareKun(kun, acc)) {
                return false;
            }
        }
        return true;
    }

    static boolean compareKun(String acc, String kun) {
        String pref1 = safeSplit(acc);
        String pref2 = safeSplit(kun);
        if (pref1.equals(pref2)) {
            return true;
        }
        if ((pref1.length()>=2) && (acc.contains("."))) {
            String bccDotRemoved = kun.replaceAll("\\.", "");
            if (bccDotRemoved.startsWith(pref1)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> simplifyKunReading(List<String> kunomi) {
        LinkedList<String> accepted = new LinkedList<String>();
        LinkedList<String> all = new LinkedList<String>();
        for (String kun : kunomi) {
            boolean accept = isNewKun(all, kun);
            if (accept) {
                accepted.add(kun);
            }
            all.add(kun);
        }
        return accepted;
    }

    public static String safeSplit(String acc) {
        String[] split = acc.split("\\.");
        return split[0];
    }
    
    public static String longer(String a, String b) {
        if (a.length() >= b.length()) {
            return a;
        } else {
            return b;
        }
    }
}
