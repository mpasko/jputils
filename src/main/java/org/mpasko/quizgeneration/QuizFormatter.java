/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.quizgeneration;

import java.util.List;

/**
 *
 * @author marcin
 */
public class QuizFormatter {

    int cnt = 1;
    StringBuilder exam = new StringBuilder();

    public String toString() {
        return exam.toString();
    }

    public void appendQuestion(String question, String correct, List<String> answers) {
        appendQuestion(new Question(question, correct, answers));
    }

    public void appendQuestion(Question question) {
        exam.append(String.format("%s) %s:\n", cnt, question.content));
        int ans_cnt = 0;
        for (String ans : question.getAnswersMixed()) {
            if (ans.equalsIgnoreCase(question.correct_answer)) {
                exam.append(">>> ");
            }
            exam.append(String.format("%s) %s\n", nextLetter(ans_cnt), ans));
            ++ans_cnt;
        }
        exam.append("\n");
        ++cnt;
    }

    public void appendQuiz(Quiz quiz) {
        quiz.getQuestions()
                .stream()
                .forEach(this::appendQuestion);
    }

    public String format(Quiz quiz) {
        appendQuiz(quiz);
        return this.toString();
    }

    private static char nextLetter(int ans_cnt) {
        return (char) ((int) ('a') + ans_cnt);
    }

}
