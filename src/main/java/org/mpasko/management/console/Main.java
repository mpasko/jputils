/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.management.console;

import org.mpasko.configuration.RuntimeSetup;

/**
 *
 * @author marcin
 */
public class Main {

    public static void main(String[] argv) {
        RuntimeSetup.configure();
        new CommandlineManager().execute(argv);
    }
}
