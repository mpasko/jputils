package org.mpasko.quizgeneration.wrongAnswers;

import org.mpasko.commons.Classifier;
import org.mpasko.exams.ExamItem;
import org.mpasko.japanese.wordcomparison.SynonimeComparer;

public class CollisionDetector {

    public boolean isSimillar(ExamItem compareThis, ExamItem compareTo) {
        if (compareThis.equals(compareTo)) {
            return false;
        }
        if (compareThis.corectAnswer.isEmpty() || compareThis.corectAnswer.isEmpty()) {
            return false;
        }
        if (hasCommonKanjiWith(compareThis.question, compareTo.question)) {
            return true;
        }
        if (Classifier.classify(compareThis.question).containsFurigana()) {
            char lastThis = compareThis.question.charAt(compareThis.question.length() - 1);
            char lastOther = compareTo.question.charAt(compareTo.question.length() - 1);
            return lastThis == lastOther;
        }
        return false;
    }

    private boolean hasCommonKanjiWith(String thisKanji, String kanji) {
        char[] oppositeArray = kanji.toCharArray();
        for (char k1 : thisKanji.toCharArray()) {
            if (Classifier.classify(k1).containsKanji()) {
                for (char k2 : oppositeArray) {
                    if (k1 == k2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isColliding(ExamItem compareThis, ExamItem compareTo) {
        return compareThis.question.contains(compareTo.question)
                || compareTo.question.contains(compareThis.question)
                || new SynonimeComparer()
                    .areSimillar(compareThis.corectAnswer, compareTo.corectAnswer);
    }
}
