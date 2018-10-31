package org.mpasko.quizgeneration.builder;

import org.mpasko.exams.ExamBuilder;

public class QuizBuilderFactory {

    public IQuizBuilder getBuilder(String activity, String phase) {
        return new GeneralPurposeBuilder();
    }
}
