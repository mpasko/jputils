/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.exams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.DictSplitter;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.quizgeneration.QuizFormatter;
import org.mpasko.japanese.wordfilters.CompoundFilter;
import org.mpasko.japanese.wordfilters.GenericFilter;
import org.mpasko.japanese.wordfilters.GradeFilter;
import org.mpasko.japanese.wordfilters.KnownWordsFilter;
import org.mpasko.japanese.wordfilters.LengthFilter;
import org.mpasko.japanese.wordfilters.OnlyKanjiFilter;
import org.mpasko.loadres.JmDictLoader;
import org.mpasko.loadres.PopularDictionaries;
import org.mpasko.quizgeneration.DictionaryToQuiz;
import org.mpasko.util.Randomation;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class GenerateExams {

    public static void main(String args[]) {
        System.getProperties().setProperty("jdk.xml.entityExpansionLimit", "0");
        int i = 0;

        /*
        //processTripleDict(String.format("dictionaries/goethe04.txt", "04"), "04");
        for (int i = 1; i<=42; ++i) { //42
            String alignedNumber = FormatterUtil.alignString(2, "0", String.valueOf(i));
            processTripleDict(String.format("dictionaries/goethe%s.txt", alignedNumber));
        }
        processAllGoethe();
         */
 /* *x/
        for (String lvl : Arrays.asList("2")) { //"N5", "N4" -missing
            processFuriganaDict(String.format("dictionaries/jlpt%s_withoutKanji.txt", lvl));
            processBlacklistDict(String.format("dictionaries/jlpt%s.txt", lvl));
        }
        /* *x/
        processTripleDict("dictionaries/all.txt");
        /* *x/
        processTripleDict("dictionaries/merged_songs.txt");
        /* */
        processTripleDict("inputs/manual_dic/japanese_april_may.txt", null);
        processTripleDict("inputs/manual_dic/japanese_april_may_yamato.txt", null);
        processTripleDict("inputs/manual_dic/japanese_june1.txt", null);
        //final GenericFilter filter = GenericFilter.buildStandardFilter();
        //processTripleDict("dictionaries/n2_tanos.txt", filter);
        /* *x/
        jmdictExam();
        /* *x/
        processFieldDicts();
        /* *x/
        processTripleDict("dictionaries/easy_ommited.csv");
        /* */
    }

    private static void processAllGoethe() {
        Dictionary mainDict = PopularDictionaries.loadGoetheDictionaries();
        String exam = DictionaryToQuiz.generateExam(mainDict);
        Util.saveFile("dictionaries/goethe_withoutKanji.txt", exam);
    }

    public static void jmdictExam() {
        JmDictLoader loader = new JmDictLoader();
        Dictionary dict = loader.load(new JmDictLoader.DefaultFilter());
        dict = GradeFilter.buildBlacklist("inputs/anki_dark_list.txt").filter(dict);
        dict = new OnlyKanjiFilter().filter(dict);
        dict = new LengthFilter(2).filter(dict);
        int desiredSize = 250;
        DictionaryToQuiz.generateExamsInParts(dict, desiredSize, "jmdict_blacklist");
    }

    private static void processFieldDicts() {
        processTripleDict("dictionaries/fields/math.txt");
        processTripleDict("dictionaries/fields/anat.txt");
        processTripleDict("dictionaries/fields/chem.txt");
        processTripleDict("dictionaries/fields/geom.txt");
        processTripleDict("dictionaries/fields/med.txt");
        processTripleDict("dictionaries/fields/physics.txt");
        processTripleDict("dictionaries/fields/sports.txt");

        OnlyKanjiFilter kanji = new OnlyKanjiFilter();
        LengthFilter length = new LengthFilter(1, 3);
        processTripleDict("dictionaries/fields/comp.txt", new CompoundFilter(kanji).and(length));
    }

    public static void processFuriganaDict(String filename) {
        final CompoundFilter filter = new CompoundFilter(OnlyKanjiFilter.katakanaFilter());
        processTripleDict(filename, filter.and(KnownWordsFilter.build()));
    }

    public static void processBlacklistDict(String filename) {
        final GradeFilter grade = GradeFilter.buildBlacklist("inputs/anki_dark_list.txt");// GradeFilter.build(5,6);
        final KnownWordsFilter known = KnownWordsFilter.build();
        processTripleDict(filename, new CompoundFilter(grade).and(known));
    }

    public static void processTripleDict(String filename, GenericFilter filter) {
        System.out.println(filename);
        Dictionary dict = Dictionary.loadTripleDict(filename);
        if (filter != null) {
            dict = filter.filter(dict);
        }
        String keyword = extractFilename(filename);
        int desiredSize = 300;
        DictionaryToQuiz.generateExamsInParts(dict, desiredSize, keyword);
    }

    public static void processTripleDict(String filename) {
        processTripleDict(filename, new OnlyKanjiFilter());
    }


    private static String extractFilename(String filename) {
        String[] array = filename.split("/");
        return array[array.length - 1].split("\\.")[0];
    }
}
