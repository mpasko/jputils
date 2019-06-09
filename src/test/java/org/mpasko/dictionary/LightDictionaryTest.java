/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import org.junit.*;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.LightDictionary;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.complex.ListeningFormatter;
import testutils.DictionaryRepository;

import java.util.LinkedList;

/**
 *
 * @author marcin
 */
public class LightDictionaryTest {

    public LightDictionaryTest() {
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
        LightDictionary dict = new LightDictionary();
        dict.put("洞窟", "どうくつ", "cave");
        DictEntry result = dict.find("洞窟", "どうくつ");
        Assert.assertEquals("cave", result.serializedMeanings());
    }

    @Test
    public void given_two_items_when_exact_matches_then_get_results() {
        LightDictionary dict = new LightDictionary();
        dict.put("物語", "ものがたり", "story");
        dict.put("洞窟", "どうくつ", "cave");
        DictEntry result = dict.find("洞窟", "どうくつ");
        Assert.assertEquals("cave", result.serializedMeanings());
    }

    @Test
    public void given_multiple_entries_when_exact_matches_then_get_results() {
        LightDictionary dict = buildShortLightDictionary();
        DictEntry result = dict.find("質実剛健", "しつじつ");
        Assert.assertEquals("unaffected and sincere", result.serializedMeanings());
        result = dict.find("確固", "かっこ");
        Assert.assertEquals("firm", result.serializedMeanings());
        result = dict.find("克己", "こっき");
        Assert.assertEquals("independent", result.serializedMeanings());
    }

    @Test
    public void given_multiple_entries_when_does_not_match_then_should_be_null() {
        LightDictionary dict = buildShortLightDictionary();
        DictEntry result = dict.find("洞窟", "どうくつ");
        Assert.assertEquals(null, result);
    }

    private static LightDictionary buildShortLightDictionary() {
        LightDictionary dict = new LightDictionary();
        dict.put("確固", "かっこ", "firm");
        dict.put("感に敏", "かんにびん", "sharp senses");
        dict.put("機に発し", "きにはっし", "by the chance");
        dict.put("質実剛健", "しつじつ", "unaffected and sincere");
        dict.put("克己", "こっき", "independent");
        dict.put("涵養", "かんよう", "cultivate");
        return dict;
    }
}
