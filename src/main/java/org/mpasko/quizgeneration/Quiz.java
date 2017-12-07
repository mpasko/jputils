/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.quizgeneration;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author marcin
 */
public class Quiz {

    private final List<Question> questions = new LinkedList<>();

    public List<Question> getQuestions() {
        return new LinkedList<>(questions);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}
