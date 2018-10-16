/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.management.parametercase;

import org.mpasko.japanese.runners.runonce.InteractiveDictionary;

import java.util.List;

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
    public String category() {
        return "search";
    }

    @Override
    public int parametersCount() {
        return 0;
    }

    @Override
    public void doTheJob(List<String> paramValue) {
        new InteractiveDictionary().start();
    }

}
