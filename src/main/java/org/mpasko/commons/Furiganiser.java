/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class Furiganiser {

    public String furiganise(String in) {
        String operate = in;
        String trans = Util.loadFile("xml/kana_transform.txt");
        for (String line : trans.split("\n")) {
            String from = line.split("-")[0];
            String to = line.split("-")[1];
            operate = operate.replaceAll(from, to);
        }
        return operate;
    }

    public String romanize(String in) {
        String operate = in;
        String trans = Util.loadFile("xml/kana_transform_reverse.txt");
        for (String line : trans.split("\n")) {
            operate = romanizeUsingLine(line, operate);
        }
        return operate;
    }

    private String romanizeUsingLine(String line, String operate) {
        final String[] splitted = line.split("-");
        String from = splitted[1];
        String to = splitted[0];
        return operate.replaceAll(from, to);
    }

    public String katakanaToHiragana(String fragment) {
        return this.furiganise(this.romanize(fragment));
    }
}
