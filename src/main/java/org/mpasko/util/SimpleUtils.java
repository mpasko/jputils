/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 *
 * @author marcin
 */
public class SimpleUtils {

    public static String clear(String string) {
        String charsRemoved = string.replaceAll("-", "").replaceAll("\n", "").replaceAll("\r", "");
        String comasUnified = charsRemoved.replaceAll(";", ",").replaceAll(", ", ",");
        return comasUnified.trim();
    }
    
    public static String limitComas(String in) {
        String[] array = in.split(",");
        if (array.length>4) {
            return MessageFormat.format("{0},{1},{2},{3}", array[0], array[1], array[2], array[3]);
        } else {
            return in;
        }
    }
    
    public static String hexToUtf(String hex) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < hex.length(); i += 2) {
          String str = hex.substring(i, i + 2);
          int byteVal = Integer.parseInt(str, 16);
          baos.write(byteVal);
        } 
        String s = new String(baos.toByteArray(), Charset.forName("UTF-8"));
        return s;
    }
}
