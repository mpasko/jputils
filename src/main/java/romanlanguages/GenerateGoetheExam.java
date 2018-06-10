/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package romanlanguages;

import java.util.ArrayList;
import java.util.List;

import org.mpasko.quizgeneration.QuizFormatter;
import org.mpasko.util.*;
import romanlanguages.commons.SimpleDictionary;
import romanlanguages.commons.SimpleEntry;

/**
 *
 * @author marcin
 */
public class GenerateGoetheExam {
    public static void main(String args[]) {
        //createExamFromDictionary("goethe_italiano_selected.txt");
        //createExamFromDictionary("custom-italiano-4.txt");
        //createExamFromDictionary("custom-italiano-5.txt");
        //createExamFromDictionary("italiano-dante-pt1.txt");
        //createExamFromDictionary("angielski-z-testu.txt");
        //createExamFromDictionary("italiano-english-test.txt");
        createExamFromDictionary("jp_listening.txt");
    }

    public static void createExamFromDictionary(String name) {
        String filename = "dictionaries/"+name;
        SimpleDictionary dict = SimpleDictionary.loadDict(filename);
        //dict = new SimillarityFilter().filter(dict);
        QuizFormatter quiz = new QuizFormatter();
        for (SimpleEntry item : dict.items()) {
            List<SimpleEntry> all = dict.items();
            all.remove(item);
            ArrayList<SimpleEntry> possibleAnswers = new ArrayList(all);
            Randomation.shuffle(possibleAnswers);
            ArrayList<String> answers = new ArrayList<String>();
            for (int i = 0; i<=8; ++i) {
                SimpleEntry answer = possibleAnswers.remove(possibleAnswers.size()-1);
                answers.add(answer.translation);
            }
            answers.add(0, item.translation);
            Randomation.shuffle(answers);
            quiz.appendQuestion(item.word, item.translation, answers);
        }
        new Filesystem().saveFile("exams/"+name, quiz.toString());
    }
}
