/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console;

/**
 *
 * @author marcin
 */
public class Main {

    public static void main(String[] argv) {
        System.getProperties().setProperty("jdk.xml.entityExpansionLimit", "0");
        new CommandlineManager().execute(argv);
    }
}
