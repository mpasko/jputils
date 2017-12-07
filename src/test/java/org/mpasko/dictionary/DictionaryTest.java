/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;

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
}
