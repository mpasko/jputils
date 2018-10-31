package org.mpasko.quizgeneration.builder;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.exams.ExamData;
import org.mpasko.exams.ExamItem;
import org.mpasko.quizgeneration.Question;
import org.mpasko.quizgeneration.Quiz;
import org.mpasko.quizgeneration.legacy.WrongAnswerSelector;
import org.mpasko.quizgeneration.wrongAnswers.GeneralPurposeSelector;
import org.mpasko.util.Randomation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralPurposeBuilder implements IQuizBuilder{

    final int TOTAL_ANSWERS = 15;

    @Override
    public Quiz buildQuiz(List<ExamItem> exam, List<ExamItem> badAnswerPool) {
        Quiz quiz = new Quiz();
        for (ExamItem entry : exam) {
            ArrayList<ExamItem> answers = prepareAllAnswers(entry, badAnswerPool, TOTAL_ANSWERS);
            String question = entry.question;
            List<String> englishAnswers = answers.stream()
                    .map(ans-> ans.corectAnswer)
                    .collect(Collectors.toList());
            quiz.addQuestion(new Question(question, entry.corectAnswer, englishAnswers));
        }
        return quiz;
    }

    private static ArrayList<ExamItem> prepareAllAnswers(ExamItem entry, List<ExamItem> dict, final int ANS) {
        GeneralPurposeSelector selector = new GeneralPurposeSelector();
        ArrayList<ExamItem> wrongAnswers = selector.getWrongAnswers(entry, dict);
        ArrayList<ExamItem> answers = cutListToDesiredSize(wrongAnswers, ANS);
        if (answers.size() < 4) {
            throw new RuntimeException("Error! too little wrong answers!");
        }
        Randomation.shuffle(answers);
        return answers;
    }

    public static <T> ArrayList<T> cutListToDesiredSize(ArrayList<T> wrongAnswers, final int ANS) {
        ArrayList<T> answers = new ArrayList<>();
        if (wrongAnswers.size() > ANS) {
            answers.addAll(wrongAnswers.subList(0, ANS - 1));
        } else {
            answers.addAll(wrongAnswers);
        }
        return answers;
    }
}
