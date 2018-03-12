/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons.analizers;

import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import testutils.DictionaryComparer;

/**
 *
 * @author marcin
 */
public class WordsExtractorTest {

    public WordsExtractorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testExtractFromText() {
        System.out.println("extractFromText");
        String text = "私わケーキ、パンが好きです";
        final Dictionary dictionary = generateDictionary();
        WordsExtractor instance = new WordsExtractor(dictionary);
        List<DictEntry> result = instance.extractFromText(text);
        DictionaryComparer.assertSame(dictionary, new Dictionary(result));
    }

    @Test
    public void testExtractFromSentence() {
        System.out.println("extractFromSentence");
        String sentence = "私わ好きです";
        final Dictionary dictionary = generateDictionary();
        WordsExtractor instance = new WordsExtractor(dictionary);
        List<DictEntry> result = instance.extractFromSentence(sentence);
        DictionaryComparer.assertSame(dictionary, new Dictionary(result));
    }

    private static Dictionary generateDictionary() {
        final Dictionary dictionary = new Dictionary();
        dictionary.put("私", "わたし", "me");
        dictionary.put("好く", "すく", "like");
        return dictionary;
    }

}
