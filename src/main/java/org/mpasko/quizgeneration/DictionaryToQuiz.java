/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.quizgeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.DictSplitter;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.util.Randomation;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class DictionaryToQuiz {

    public static void generateExamsInParts(Dictionary dict, int desiredSize, String keyword) {
        if (dict.size() > desiredSize) {
            List<Dictionary> parts = new DictSplitter().split(dict, desiredSize);
            int cnt = 0;
            for (Dictionary part : parts) {
                String exam = generateExam(part);
                Util.saveFile(String.format("exams/%s_part%s.txt", keyword, ++cnt), exam);
            }
        } else {
            String exam = generateExam(dict);
            Util.saveFile("exams/" + keyword + ".txt", exam);
        }
    }

    private static ArrayList<DictEntry> prepareAllAnswers(DictEntry entry, Dictionary dict, final int ANS) {
        ArrayList<DictEntry> wrongAnswers = new WrongAnswerSelector().getWrongAnswers(entry, dict);
        ArrayList<DictEntry> answers = cutListToDesiredSize(wrongAnswers, ANS);
        if (answers.size() < 4) {
            throw new RuntimeException("Error! too little wrong answers!");
        }
        //answers.add(entry);
        Randomation.shuffle(answers);
        return answers;
    }

    public static String generateExam(Dictionary dict) {
        final int TOTAL_ANSWERS = 15;
        Quiz quiz = new DictionaryToQuiz(dict, TOTAL_ANSWERS).generateQuiz();
        return new QuizFormatter().format(quiz);
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

    private final Dictionary dictionary;
    private final int expectedAnswers;

    public DictionaryToQuiz(Dictionary dict, int expectedAnswers) {
        this.dictionary = dict;
        this.expectedAnswers = expectedAnswers;
    }

    public Quiz generateQuiz() {
        Quiz quiz = new Quiz();
        for (DictEntry entry : dictionary.items()) {
            ArrayList<DictEntry> answers = prepareAllAnswers(entry, dictionary, expectedAnswers);
            String question = entry.generateQuestion();
            List<String> englishAnswers = answers.stream().map((DictEntry ans) -> ans.english).collect(Collectors.toList());
            quiz.addQuestion(new Question(question, entry.english, englishAnswers));
        }
        return quiz;
    }
}
