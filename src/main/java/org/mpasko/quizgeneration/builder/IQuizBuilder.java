package org.mpasko.quizgeneration.builder;

import org.mpasko.exams.ExamData;
import org.mpasko.exams.ExamItem;
import org.mpasko.quizgeneration.Quiz;

import java.util.List;

public interface IQuizBuilder {
    public Quiz buildQuiz(List<ExamItem> exam, List<ExamItem> badAnswerPool);
}
