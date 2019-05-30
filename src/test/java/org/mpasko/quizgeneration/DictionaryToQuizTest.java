/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.quizgeneration;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.exams.ExamBuilder;
import org.mpasko.exams.ExamItem;
import org.mpasko.quizgeneration.builder.GeneralPurposeBuilder;

import java.util.List;

/**
 *
 * @author marcin
 */
public class DictionaryToQuizTest {

    private static Dictionary regularDictionary;

    public DictionaryToQuizTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        regularDictionary = new Dictionary();
        regularDictionary.put("嘗てない", "かつてない", "unbearable");
        regularDictionary.put("側面", "そくめん", "aspect");
        regularDictionary.put("仕組み", "しくみ", "structure");
        regularDictionary.put("絵柄", "えがら", "pattern");
        regularDictionary.put("よって", "よって", "therefore");
        regularDictionary.put("僧侶", "そうりょ", "monk,priest");
        regularDictionary.put("牟", "ぼう", "pupil of eye");
        regularDictionary.put("廉", "れん", "bargain");
        regularDictionary.put("悦ぶ", "よろこぶ", "happiness");
        regularDictionary.put("倒錯", "とうさく", "perversion");
        regularDictionary.put("縁", "えにし", "driven by fate,making connection");
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGenerateQuiz() {
        basicTestForActivity("reading");
        basicTestForActivity("listening");
    }

    private static void basicTestForActivity(String activity) {
        System.out.println("generateQuiz:"+activity);
        GeneralPurposeBuilder builder = new GeneralPurposeBuilder();
        List<ExamItem> exam = new ExamBuilder().dictionaryIntoExam(regularDictionary.items(), activity);
        Quiz result = builder.buildQuiz(exam, exam);
        assertEquals("expected one question per dict entry", regularDictionary.size(), result.getQuestions().size());
        assertAnswersNotEmpty(result);
    }

    static void assertAnswersNotEmpty(Quiz result) {
        for (Question question : result.getQuestions()) {
            assertNotSame("", question.content);
            assertNotSame("", question.correct_answer);
            assertTrue(question.wron_answers.size() > 3);
        }
    }

}
