/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marcin
 */
public class FuriganiserTest {

    public FuriganiserTest() {
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

    /**
     * Test of furiganise method, of class Furiganiser.
     */
    @Test
    public void testFuriganise() {
        System.out.println("furiganise");
        Furiganiser instance = new Furiganiser();
        assertEquals("かんっぺき", instance.furiganise("kanppeki"));
        assertEquals("しょうめい", instance.furiganise("shoumei"));
        assertEquals("ざんぜん", instance.furiganise("zanzen"));
        assertEquals("あかひばら", instance.furiganise("akahibara"));
        assertEquals("りゅうし", instance.furiganise("ryuushi"));
        assertEquals("きょうだい", instance.furiganise("kyoudai"));
        assertEquals("ひめい", instance.furiganise("himei"));
        assertEquals("こっちょう", instance.furiganise("kocchou"));
    }

    @Test
    public void testRomanise() {
        System.out.println("testRomanise");
        Furiganiser instance = new Furiganiser();
        assertEquals("kanppeki", instance.romanize("かんっぺき"));
        assertEquals("shoumei", instance.romanize("しょうめい"));
        assertEquals("zanzen", instance.romanize("ざんぜん"));
        assertEquals("akahibara", instance.romanize("あかひばら"));
        assertEquals("ryuushi", instance.romanize("りゅうし"));
        assertEquals("kyoudai", instance.romanize("きょうだい"));
        assertEquals("himei", instance.romanize("ひめい"));
        assertEquals("kocchou", instance.romanize("こっちょう"));
    }

    @Test
    public void testRomaniseKatakana() {
        System.out.println("testRomaniseKatakana");
        Furiganiser instance = new Furiganiser();
        assertEquals("kanppeki", instance.romanize("カンッペキ"));
        assertEquals("shoumei", instance.romanize("ショウメイ"));
        assertEquals("zanzen", instance.romanize("ザンゼン"));
        assertEquals("akahibara", instance.romanize("アカヒバラ"));
        assertEquals("ryuushi", instance.romanize("リュウシ"));
        assertEquals("kyoudai", instance.romanize("キョウダイ"));
        assertEquals("himei", instance.romanize("ヒメイ"));
    }
}
