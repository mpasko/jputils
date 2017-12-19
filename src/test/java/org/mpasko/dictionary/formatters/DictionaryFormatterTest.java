/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.formatters;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.dictionary.Dictionary;

/**
 *
 * @author marcin
 */
public class DictionaryFormatterTest {

    public DictionaryFormatterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testListeningFormat() {
        System.out.println("testListeningFormat");
        Dictionary dict = new Dictionary();
        dict.put("茹だる", "うだる", "boil,seathe");
        final String[] expected = new String[]{"うだる", "boil,seathe"};
        DictionaryFormatter instance = DictionaryFormatter.buildListeningFormatter();
        assertFormattingCorrect(instance, dict, expected);
    }

    @Test
    public void testStandardFormat() {
        System.out.println("testStandardFormat");
        Dictionary dict = new Dictionary();
        dict.put("茹だる", "うだる", "boil,seathe");
        final String[] expected = new String[]{"茹だる うだる", "boil,seathe"};
        DictionaryFormatter instance = DictionaryFormatter.buildStandardFormatter();
        assertFormattingCorrect(instance, dict, expected);
    }

    private void assertFormattingCorrect(DictionaryFormatter instance, Dictionary dict, final String[] expected) {
        String result = instance.format(dict).replaceAll("\n", "");
        assertEquals("Expected separator '-'", true, result.contains("-"));
        assertArrayEquals(expected, result.split("-"));
    }

}
