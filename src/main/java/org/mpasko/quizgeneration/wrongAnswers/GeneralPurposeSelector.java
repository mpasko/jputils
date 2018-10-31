package org.mpasko.quizgeneration.wrongAnswers;

import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.exams.ExamItem;
import org.mpasko.util.Randomation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GeneralPurposeSelector implements IWrongAnswerSelector {

    @Override
    public ArrayList<ExamItem> getWrongAnswers(ExamItem question, List<ExamItem> badAnswerPool) {
        ArrayList<ExamItem> answers = new ArrayList<ExamItem>();
        addSimillar(question, answers, badAnswerPool);
        int deficit = 10 - answers.size();
        if (deficit > 0) {
            addAnyRandom(question, answers, badAnswerPool, deficit);
        }
        return answers;
    }

    void addAnyRandom(ExamItem question, ArrayList<ExamItem> answers, List<ExamItem> dictionary, int desiredSize) {
        LinkedList<ExamItem> candidates = new LinkedList<ExamItem>();
        for (ExamItem item : dictionary) {
            if (!answers.contains(item) && !new CollisionDetector().isColliding(question, item)) {
                candidates.add(item);
            }
        }
        if (candidates.size() > desiredSize) {
            for (int i = 0; i < desiredSize; ++i) {
                ExamItem next = Randomation.choose(candidates);
                answers.add(next);
                candidates.remove(next);
            }
        } else {
            ArrayList<ExamItem> tmp = new ArrayList<ExamItem>(dictionary);
            tmp.removeAll(answers);
            while (tmp.size() + answers.size() > 6) {
                tmp.remove(Randomation.choose(tmp));
            }
            answers.addAll(tmp);
        }
    }

    void addSimillar(ExamItem entry, ArrayList<ExamItem> answers, List<ExamItem> dictionary) {
        CollisionDetector detector = new CollisionDetector();
        for (ExamItem item : dictionary) {
            if (detector.isSimillar(item, entry) && !detector.isColliding(item, entry)) {
                answers.add(item);
            }
        }
    }
}
