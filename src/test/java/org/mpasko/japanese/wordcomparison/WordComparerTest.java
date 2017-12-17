/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordcomparison;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class WordComparerTest {

    public WordComparerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testAreSimillar() {
        System.out.println("areSimillarGrammatically");
        assertAreSimillarGrammatically("聞く", "きく", "", "聞き", "きき", "", true);
        assertAreSimillarGrammatically("大切", "だいせつ", "", "大切な", "だいせつな", "", true);
    }

    @Test
    public void testAreDifferent() {
        System.out.println("areDifferentGrammatically");
        assertAreSimillarGrammatically("聞", "聞せつ", "", "聞き", "きき", "", false);
        assertAreSimillarGrammatically("大切", "だいせつ", "", "大説", "だいせつ", "", false);
    }

    @Test
    public void testSynonims() {
        System.out.println("areSynonims");
        assertSynonims("", "", "to differ", "", "", "different", true);
        assertSynonims("", "", "exchange student", "", "", "exchanged students", true);
    }

    @Test
    public void testNoSynonims() {
        System.out.println("areNoSynonims");
        assertSynonims("", "", "to differ", "", "", "to exchange", false);
        assertSynonims("", "", "to come", "", "", "to commute", false);
    }

    private static void assertAreSimillarGrammatically(String kanji1, String writing1, String english1, String kanji2, String writing2, String english2, boolean expected) {
        IWordComparer instance = new GrammaticalComparer();
        assertAre(kanji1, writing1, english1, kanji2, writing2, english2, instance, expected);
    }

    private static void assertSynonims(String kanji1, String writing1, String english1, String kanji2, String writing2, String english2, boolean expected) {
        IWordComparer instance = new SynonimeComparer();
        assertAre(kanji1, writing1, english1, kanji2, writing2, english2, instance, expected);
    }

    private static void assertAre(String kanji1, String writing1, String english1, String kanji2, String writing2, String english2, IWordComparer instance, boolean expResult) {
        DictEntry entry1 = new DictEntry(kanji1, writing1, english1);
        DictEntry entry2 = new DictEntry(kanji2, writing2, english2);
        boolean result = instance.areSimillar(entry1, entry2);
        assertEquals(expResult, result);
    }

}
