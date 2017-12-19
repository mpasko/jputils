/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.runonce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.loadres.JmDictLoader;

/**
 *
 * @author marcin
 */
public class InteractiveDictionary {

    public static void main(String[] args) {
        new InteractiveDictionary().start();
    }

    public void start() {
        try {
            Dictionary dict = new JmDictLoader().load(new JmDictLoader.DefaultFilter());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String s;
            while ((s = in.readLine()) != null && s.length() != 0) {
                searchAndPrintResults(s, dict);
            }
        } catch (IOException ex) {
            Logger.getLogger(InteractiveDictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void searchAndPrintResults(String phrase, Dictionary dict) {
        System.out.println("Searching for: " + phrase);
        final LinkedList<DictEntry> results = dict.findAllByReading(phrase);
        results
                .forEach(entry -> System.out.println(entry.toString()));
        if (results.isEmpty()) {
            System.out.println("Nothing found!");
        }
        // An empty line or Ctrl-Z terminates the program
    }
}
