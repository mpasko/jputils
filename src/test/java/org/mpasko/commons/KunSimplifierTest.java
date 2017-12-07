/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marcin
 */
public class KunSimplifierTest {
    
    public KunSimplifierTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of compareAll method, of class KunSimplifier.
     */
    @Test
    public void testisNewKunFalse() {
        System.out.println("isNewKun");
        LinkedList<String> accepted = new LinkedList<String>(Arrays.asList("こと.わり","こと.わる"));
        String kun = "こと.わらう";
        boolean result = KunSimplifier.isNewKun(accepted, kun);
        assertEquals(false, result);
    }
    
    @Test
    public void testisNewKunTrue() {
        System.out.println("isNewKun");
        LinkedList<String> accepted = new LinkedList<String>(Arrays.asList("こと.わり","こと.わる"));
        String kun = "ない.わらう";
        boolean result = KunSimplifier.isNewKun(accepted, kun);
        assertEquals(true, result);
    }

    /**
     * Test of compareKun method, of class KunSimplifier.
     */
    @Test
    public void testCompareKun() {
        System.out.println("compareKun");
        assertEquals(true, KunSimplifier.compareKun("こと.わり", "こと.わる"));
        assertEquals(true, KunSimplifier.compareKun("こと.わり", "こと.わらう"));
        assertEquals(false, KunSimplifier.compareKun("こと.わり", "ない.わる"));
        
        assertEquals(true, KunSimplifier.compareKun("とら.われる", "と.らえる"));
        assertEquals(true, KunSimplifier.compareKun("ひか.る", "ひかり"));
        
    }

    /**
     * Test of simplifyKunReading method, of class KunSimplifier.
     */
    @Test
    public void testSimplifyKunReadingOneLeft() {
        System.out.println("testSimplifyKunReadingOneLeft");
        List<String> kunomi = new LinkedList<String>(Arrays.asList("こと.わり","こと.わる","こと.わらう"));
        List<String> result = KunSimplifier.simplifyKunReading(kunomi);
        assertEquals(1, result.size());
    }
    
    @Test
    public void testSimplifyKunReadingTwoLeft() {
        System.out.println("testSimplifyKunReadingTwoLeft");
        List<String> kunomi = new LinkedList<String>(Arrays.asList("こと.わり","こと.わる","こと.わらう","ない.わらう"));
        List<String> result = KunSimplifier.simplifyKunReading(kunomi);
        assertEquals(2, result.size());
    }
}
