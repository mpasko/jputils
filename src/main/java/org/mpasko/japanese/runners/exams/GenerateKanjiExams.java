/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.exams;

import org.mpasko.loadres.KanjiDictLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.loadres.KanjiDictLoader.Filter;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.KanjiEntry;
import org.mpasko.quizgeneration.QuizFormatter;
import org.mpasko.japanese.kanjifilters.DictFilter;
import org.mpasko.japanese.runners.KanjiListFromText;
import org.mpasko.loadres.PopularDictionaries;
import org.mpasko.util.Filesystem;
import org.mpasko.util.Randomation;

/**
 *
 * @author marcin
 */
public class GenerateKanjiExams {
    public static void main(String args[]) {
        for (int i = 5; i<7; ++i) {
            //generateExamForGrade(i, i);
        }
        //generateExamForGrade(2, 4);
        //generateExamForGrade(3, 5);
        //generateExamForGrade(8, 8);
        generateExamForDict(PopularDictionaries.loadGoetheDictionaries(), "goethe");
        generateExamForDict(PopularDictionaries.loadFieldDictionaries(), "fields");
        for (String song : KanjiListFromText.chinese_songs) {
            generateExamForChineseSong(song);
        }
    }

    public static void generateExamForGrade(int gradeFrom, int gradeTo) {
        Filter filter = new Filter();
        filter.gradeFrom = gradeFrom;
        filter.gradeTo = gradeTo;
        KanjiDictionary dict = new KanjiDictLoader().load(filter);
        String filename = "exams/kanji_grade"+gradeFrom+"-"+gradeTo+".txt";
        new Filesystem().saveFile(filename, generateKanjiExam(dict).toString());
    }
    
    public static void generateExamForDict(Dictionary wordDict, String name) {
        Filter filter = new Filter();
        filter.gradeFrom = 8;
        filter.gradeTo = 8;
        KanjiDictionary dict = new KanjiDictLoader().load(filter);
        dict = new DictFilter(wordDict).filter(dict);
        String filename = "exams/kanji_from_dict_"+name+".txt";
        new Filesystem().saveFile(filename, generateKanjiExam(dict).toString());
    }
    
    public static void generateExamForChineseSong(String name) {
        KanjiDictionary dict = KanjiListFromText.loadChineseSong(name);
        String filename = "exams/kanji_chinese_song_"+name+".txt";
        new Filesystem().saveFile(filename, generateKanjiExam(dict).toString());
    }

    public static QuizFormatter generateKanjiExam(KanjiDictionary dict) {
        QuizFormatter exam = new QuizFormatter();
        List<KanjiEntry> items = dict.getItems();
        for (KanjiEntry item : items) {
            
            ArrayList<KanjiEntry> wrong = new ArrayList<KanjiEntry>();
            while(wrong.size()<6) {
                KanjiEntry chosen = Randomation.choose(items);
                if (chosen != item) {
                    wrong.add(chosen);
                }
            }
            wrong.add(item);
            Randomation.shuffle(wrong);
            List<String> formatted = new LinkedList<String>();
            for (KanjiEntry q : wrong) {
                formatted.add(q.formatAnswer());
            }
            exam.appendQuestion(item.character, item.formatAnswer(), formatted);
        }
        return exam;
    }
}
