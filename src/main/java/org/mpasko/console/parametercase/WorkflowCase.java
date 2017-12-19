/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console.parametercase;

import org.mpasko.japanese.runners.workflow.ProcessEverything;

/**
 *
 * @author marcin
 */
public class WorkflowCase implements IParameterCase {

    @Override
    public String name() {
        return "workflow";
    }

    @Override
    public String description() {
        return "Updated workflow";
    }

    @Override
    public boolean hasParameter() {
        return false;
    }

    @Override
    public void doTheJob(String paramValue) {
        new ProcessEverything().start();
    }

}
