/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console.parametercase;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 *
 * @author marcin
 */
public class ParameterCaseVerifier {

    private final IParameterCase parameterCase;

    public ParameterCaseVerifier(IParameterCase parameterCase) {
        this.parameterCase = parameterCase;
    }

    public void setup(Options options) {
        options.addOption(parameterCase.name(), getShort(), parameterCase.hasParameter(), parameterCase.description());
    }

    public boolean doJobIfNeeded(CommandLine cmd) {
        if (cmd.hasOption(parameterCase.name())) {
            String parameter = "";
            if (parameterCase.hasParameter()) {
                parameter = cmd.getOptionValue(parameterCase.name());
            }
            parameterCase.doTheJob(parameter);
            return true;
        }
        return false;
    }

    private String getShort() {
        return parameterCase.name().substring(0, 1).toUpperCase();
    }
}
