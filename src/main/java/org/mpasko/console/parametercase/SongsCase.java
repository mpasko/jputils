/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console.parametercase;

import org.mpasko.japanese.runners.parsers.ParseJishoOutputs;

/**
 *
 * @author marcin
 */
public class SongsCase implements IParameterCase {

    @Override
    public String name() {
        return "songs";
    }

    @Override
    public String description() {
        return "Generate songs";
    }

    @Override
    public boolean hasParameter() {
        return false;
    }

    @Override
    public void doTheJob(String paramValue) {
        ParseJishoOutputs.processAllSongs();
    }

}
