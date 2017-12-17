/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.japanese.wordcomparison.SamePhonetic;

/**
 *
 * @author marcin
 */
public class DuplicateFilterTest {

    public DuplicateFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testItemMatches() {
        System.out.println("itemMatches");
        DuplicateFilter instance = new DuplicateFilter(new SamePhonetic());
        assertEquals(true, instance.itemMatches(new DictEntry("正銘", "しょうめい", "")));
        assertEquals(true, instance.itemMatches(new DictEntry("消滅", "しょうめつ", "")));
        assertEquals(false, instance.itemMatches(new DictEntry("証明", "しょうめい", "")));
    }

}
