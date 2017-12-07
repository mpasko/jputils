/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko;

import org.mpasko.japanese.runners.parsers.ParseJishoOutputs;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcin
 */
public class ParseJishoOutputsTest {
    
    public ParseJishoOutputsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of mergeFuriganaAndKana method, of class ParseJishoOutputs.
     */
    @Test
    public void testMergeFuriganaAndKanaAllKana() {
        System.out.println("mergeFuriganaAndKanaAllKana");
        String kana = "立法権";
        String furigana = "りっぽうけん";
        String expResult = "りっぽうけん";
        String result = ParseJishoOutputs.mergeFuriganaAndKana(kana, furigana);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testMergeFuriganaAndKanaEnding() {
        System.out.println("testMergeFuriganaAndKanaEnding");
        String kana = "立ち売りた";
        String furigana = "たちう";
        String expResult = "たちうりた";
        String result = ParseJishoOutputs.mergeFuriganaAndKana(kana, furigana);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testMergeFuriganaAndKanaBeginning() {
        System.out.println("testMergeFuriganaAndKanaBeginning");
        String kana = "たり立ち売";
        String furigana = "たちう";
        String expResult = "たりたちう";
        String result = ParseJishoOutputs.mergeFuriganaAndKana(kana, furigana);
        assertEquals(expResult, result);
    }
}
