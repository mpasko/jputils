/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.quizgeneration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.mpasko.util.Randomation;

/**
 *
 * @author marcin
 */
public class Question {

    public String content = "";
    public List<String> wron_answers = new LinkedList<>();
    public String correct_answer = "";

    Question(String question, String correct, List<String> answers) {
        content = question;
        correct_answer = correct;
        wron_answers.addAll(answers);
        wron_answers.removeIf(item -> item.equalsIgnoreCase(correct));
    }

    public List<String> getAnswersMixed() {
        ArrayList<String> result = new ArrayList<>(wron_answers);
        result.add(correct_answer);
        Randomation.shuffle(result);
        return result;
    }
}
