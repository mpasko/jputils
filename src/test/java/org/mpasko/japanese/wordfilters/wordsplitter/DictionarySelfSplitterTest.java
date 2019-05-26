/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters.wordsplitter;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.parseTexts.splitters.DictionarySplitter;
import testutils.DictionaryComparer;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author marcin
 */
public class DictionarySelfSplitterTest {

    public DictionarySelfSplitterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void when_in_wrong_order_then_should_split() {
        System.out.println("wrong order split");
        Dictionary inputDic = new Dictionary();
        inputDic.put("臨機", "environment");
        inputDic.put("臨機応変", "adapting oneself to environment");
        inputDic.put("応変", "ansewring, response");
        DictionarySelfFilter filter = new DictionarySelfFilter();
        Dictionary resultDic = filter.filter(inputDic);
        Assert.assertEquals("Expected lond word to be filtered", 2, resultDic.size());
    }



    @Test
    public void when_in_odd_length_then_should_split() {
        System.out.println("odd split");
        Dictionary inputDic = new Dictionary();
        inputDic.put("ab", "");
        inputDic.put("abcde", "");
        inputDic.put("cde", "");
        DictionarySelfFilter filter = new DictionarySelfFilter();
        Dictionary resultDic = filter.filter(inputDic);
        Assert.assertEquals("Expected lond word to be filtered", 2, resultDic.size());
    }
}
