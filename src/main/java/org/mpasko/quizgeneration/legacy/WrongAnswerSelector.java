/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.quizgeneration.legacy;

import java.util.ArrayList;
import java.util.LinkedList;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.util.Randomation;

/**
 *
 * @author marcin
 */
@Deprecated
public class WrongAnswerSelector {

    void addAnyRandom(DictEntry question, ArrayList<DictEntry> answers, Dictionary dictionary) {
        LinkedList<DictEntry> candidates = new LinkedList<DictEntry>();
        for (DictEntry item : dictionary.items()) {
            if (!answers.contains(item) && !item.isColliding(question)) {
                candidates.add(item);
            }
        }
        if (candidates.size() >= 7) {
            for (int i = 0; i < 6; ++i) {
                DictEntry next = Randomation.choose(candidates);
                answers.add(next);
                candidates.remove(next);
            }
        } else {
            ArrayList<DictEntry> tmp = new ArrayList<DictEntry>(dictionary.items());
            tmp.removeAll(answers);
            while (tmp.size() + answers.size() > 6) {
                tmp.remove(Randomation.choose(tmp));
            }
            answers.addAll(tmp);
        }
    }

    void addSimillar(DictEntry entry, ArrayList<DictEntry> answers, Dictionary dictionary) {
        for (DictEntry item : dictionary.items()) {
            if (item.isSimillar(entry) && !item.isColliding(entry)) {
                answers.add(item);
            }
        }
    }

    public ArrayList<DictEntry> getWrongAnswers(DictEntry entry, Dictionary dict) {
        ArrayList<DictEntry> answers = new ArrayList<DictEntry>();
        addSimillar(entry, answers, dict);
        if (answers.size() < 10) {
            addAnyRandom(entry, answers, dict);
        }
        //It is done again
        //Randomation.shuffle(answers);
        return answers;
    }

}
