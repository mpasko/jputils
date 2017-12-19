/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.WritingChooser;

/**
 *
 * @author marcin
 */
public class UniversalIndexTest {

    private Dictionary dict;

    public UniversalIndexTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
        dict = new Dictionary();
        dict.put("調査", "ちょうさ", "search");
        dict.put("新駅", "しんえき", "new station");
    }

    @Test
    public void testFindBest_ShouldFind() {
        System.out.println("testFindBest_ShouldFind");
        UniversalIndex instance = new UniversalIndex(new WritingChooser(), dict);
        DictEntry result = instance.findBest(new DictEntry("ちょうさ", "transcendence"));
        assertEquals("search", result.english);
    }

    @Test
    public void testFindBest_ShoudNotFind() {
        System.out.println("testFindBest_ShoudNotFind");
        UniversalIndex instance = new UniversalIndex(new KanjiChooser(), dict);
        DictEntry result = instance.findBest(new DictEntry("ちょうさ", "transcendence"));
        assertEquals(null, result);
    }

}
