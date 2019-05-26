/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

/**
 *
 * @author marcin
 */
public class Classifier {

    public static Statistics classify(String text) {
        Statistics statistics = new Statistics();
        for (char c : text.toCharArray()) {
            statistics.classify(c);
        }
        return statistics;
    }

    public static Statistics classify(char c) {
        Statistics statistics = new Statistics();
        statistics.classify(c);
        return statistics;
    }

}
