/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.management.parametercase;

import org.mpasko.japanese.runners.parsers.ParseJishoOutputs;

import java.util.List;

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
    public String category() {
        return "processing";
    }

    @Override
    public int parametersCount() {
        return 0;
    }

    @Override
    public void doTheJob(List<String> paramValue) {
        ParseJishoOutputs.processAllSongs();
    }

}
