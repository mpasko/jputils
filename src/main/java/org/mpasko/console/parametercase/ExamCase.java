/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.console.parametercase;

import org.mpasko.japanese.runners.exams.GenerateExams;

/**
 *
 * @author marcin
 */
public class ExamCase implements IParameterCase {

    @Override
    public String name() {
        return "exam";
    }

    @Override
    public String description() {
        return "Create Exam";
    }

    @Override
    public boolean hasParameter() {
        return true;
    }

    @Override
    public void doTheJob(String paramValue) {
        GenerateExams.processTripleDict(paramValue, null);
    }

}
