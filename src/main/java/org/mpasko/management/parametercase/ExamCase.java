/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.management.parametercase;

import org.mpasko.japanese.runners.exams.GenerateExams;

import java.util.List;

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
    public String category() {
        return "processing";
    }

    @Override
    public int parametersCount() {
        return 1;
    }

    @Override
    public void doTheJob(List<String> paramValue) {
        GenerateExams.processTripleDict(paramValue.get(0), null);
    }

}
