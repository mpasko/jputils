/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons.analizers;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marcin
 */
public class GrammarStemmerTest {

    private static GrammarStemmer instance;

    public GrammarStemmerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance = new GrammarStemmer();
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testStem() {
        System.out.println("stem");
        assertEquals("乗る", instance.stem("乗った"));
        assertEquals("好く", instance.stem("好き"));
        assertEquals("ぶらぶら", instance.stem("ぶらぶらです"));
        assertEquals("見る", instance.stem("見える"));
        assertEquals("行く", instance.stem("行こ"));
        assertEquals("寝る", instance.stem("寝ろ"));
    }

}
