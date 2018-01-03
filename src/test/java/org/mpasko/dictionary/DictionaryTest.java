/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.complex.ListeningFormatter;
import testutils.DictionaryRepository;

/**
 *
 * @author marcin
 */
public class DictionaryTest {

    public DictionaryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void when_exact_matches_then_get_results() {
        Dictionary dict = new Dictionary();
        dict.put("洞窟", "どうくつ", "cave");
        DictEntry result = dict.find("洞窟", "どうくつ");
        Assert.assertEquals("cave", result.english);
    }

    @Test
    public void strict_when_exact_matches_then_get_results() {
        Dictionary dict = new Dictionary();
        dict.put("洞窟", "どうくつ", "cave");
        DictEntry result = dict.findStrict("洞窟", "どうくつ");
        Assert.assertEquals("cave", result.english);
    }

    @Test
    public void when_phonetic_match_only_then_should_return_matching_entry() {
        Dictionary dict = new Dictionary();
        dict.put("短病", "たんびょう", "headache");
        DictEntry result = dict.find("端秒", "たんびょう");
        Assert.assertEquals("headache", result.english);
    }

    @Test
    public void when_kanji_match_only_then_should_return_matching_entry() {
        Dictionary dict = new Dictionary();
        dict.put("一人", "ひとり", "one person");
        DictEntry result = dict.find("一人", "いちじん");
        Assert.assertEquals("one person", result.english);
    }

    @Test
    public void when_no_result_then_should_return_null() {
        Dictionary dict = new Dictionary();
        dict.put("一人", "ひとり", "one person");
        DictEntry result = dict.find("端秒", "たんびょう");
        Assert.assertEquals(null, result);
    }

    @Test
    public void when_multiple_possibilities_return_the_shortest() {
        Dictionary dict = new Dictionary();
        dict.put("一人", "ひとり", "one person");
        dict.put("一人", "ひとつおまえ", "one person");
        DictEntry result = dict.find("一人", "うぜい");
        Assert.assertEquals("ひとり", result.writing);
    }

    @Test
    public void find_phonetic_using_feature_searcher() {
        Dictionary dict = DictionaryRepository.threeHomonymes();
        LinkedList<DictEntry> result = dict.findAllByFeature("tousaku", new RomajiWritingChooser());
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void find_listening_using_feature_searcher() {
        Dictionary dict = DictionaryRepository.threeHomonymes();
        LinkedList<DictEntry> result = dict.findAllByFeature("とうさく-plagiarism", new ListeningFormatter());
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("盗作", result.get(0).kanji);
    }

    @Test
    public void find_feature_and_containing() {
        Dictionary dict = DictionaryRepository.threeHomonymes();
        LinkedList<DictEntry> result = dict.findKeysAndValuesContaining("tousaku",
                "pervers",
                new RomajiWritingChooser(),
                new MeaningChooser());
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("倒錯", result.get(0).kanji);
    }

    //@Test
    public void technically() {
        //Assert.assertEquals(true, "");
    }

    @Test
    public void test_if_we_can_find_using_romaji_and_partial_kanji() {
        Dictionary dict = DictionaryRepository.threeHomonymes();
        LinkedList<DictEntry> result = dict.findKeysAndValuesContaining("adjunct word",
                "語",
                new MeaningChooser(),
                new KanjiChooser());
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("付加語", result.get(0).kanji);
    }
}
