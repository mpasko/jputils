/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marcin
 */
public class RandomationTest {

    public RandomationTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of shuffle method, of class Randomation.
     */
    @Test
    public void testShuffle() {
        System.out.println("shuffle");
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Randomation.shuffle(list);
        assertTrue(list.contains(1));
        assertTrue(list.contains(2));
        assertTrue(list.contains(3));
        assertTrue(list.contains(4));
        assertTrue(list.contains(5));
    }

    /**
     * Test of swap method, of class Randomation.
     */
    @Test
    public void testSwap() {
        System.out.println("swap");
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        Randomation.swap(list, 1, 2);
        assertEquals((int) 3, (int) list.get(1));
        assertEquals((int) 2, (int) list.get(2));
    }
}
