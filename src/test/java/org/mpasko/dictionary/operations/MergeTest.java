/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.operations;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;
import org.mpasko.dictionary.LightDictionary;
import testutils.DictionaryComparer;

/**
 *
 * @author marcin
 */
public class MergeTest {

    public MergeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void when_same_kanji_then_should_merge() {
        System.out.println("when_same_kanji_then_should_merge");
        IDictionary base = new LightDictionary();
        base.put("涼", "りょう", "cool breeze,cool air,refreshing coolness");
        base.put("涼しい", "すずしい", "cool,refreshing");

        IDictionary additional = new LightDictionary();
        additional.put("涼", "さやか", "(F) Sayaka");

        IDictionary result = new Merge().mergeDictionaries(base, additional);
        Assert.assertEquals("Should not add new", 2, result.size());
        DictEntry foundMerged = result.findDefault("涼");
        Assert.assertEquals("should append reading", "りょう,さやか", foundMerged.writing);
        Assert.assertEquals("should append english", "cool breeze,cool air,refreshing coolness,(F) Sayaka", foundMerged.english);
    }

    @Test
    public void when_different_kanji_then_should_add() {
        System.out.println("when_different_kanji_then_should_add");
        IDictionary base = new LightDictionary();
        base.put("涼", "りょう", "cool breeze,cool air,refreshing coolness");
        base.put("涼しい", "すずしい", "cool,refreshing");

        IDictionary additional = new LightDictionary();
        additional.put("留", "りゅう", "detain,fasten,stop");

        IDictionary result = new Merge().mergeDictionaries(base, additional);
        Assert.assertEquals("Should add new", 3, result.size());
    }
}
