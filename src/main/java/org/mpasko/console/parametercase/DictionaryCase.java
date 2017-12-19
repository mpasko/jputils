/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console.parametercase;

import org.mpasko.japanese.runners.runonce.InteractiveDictionary;

/**
 *
 * @author marcin
 */
public class DictionaryCase implements IParameterCase {

    @Override
    public String name() {
        return "dictionary";
    }

    @Override
    public String description() {
        return "Interactive dictionary";
    }

    @Override
    public boolean hasParameter() {
        return false;
    }

    @Override
    public void doTheJob(String paramValue) {
        new InteractiveDictionary().start();
    }

}
