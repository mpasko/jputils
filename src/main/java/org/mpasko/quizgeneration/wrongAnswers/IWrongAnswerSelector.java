package org.mpasko.quizgeneration.wrongAnswers;

import org.mpasko.exams.ExamItem;

import java.util.List;

public interface IWrongAnswerSelector {
    List<ExamItem> getWrongAnswers(ExamItem question, List<ExamItem> badAnswerPool);
}
