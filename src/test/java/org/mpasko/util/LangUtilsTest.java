/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcin
 */
public class LangUtilsTest {
    
    public LangUtilsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of getOnlyPreInfix method, of class LangUtils.
     */
    @Test
    public void testGetOnlyPreInfix() {
        System.out.println("getOnlyPreInfix");
        String kana = "跡切れる";
        String expResult = "跡切";
        String result = LangUtils.getOnlyPreInfix(kana);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetOnlyPreInfixWithBeginning() {
        System.out.println("testGetOnlyPreInfixWithBeginning");
        String kana = "るれ跡切れる";
        String expResult = "るれ跡切";
        String result = LangUtils.getOnlyPreInfix(kana);
        assertEquals(expResult, result);
    }
}
