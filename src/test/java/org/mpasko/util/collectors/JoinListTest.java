/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util.collectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author marcin
 */
public class JoinListTest {

    public JoinListTest() {
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
     * Test of supplier method, of class JoinList.
     */
    @Test
    public void testCollectsCorrectly() {
        System.out.println("testCollectsCorrectly");
        JoinList instance = new JoinList();
        LinkedList<ArrayList<String>> listsToMerge = new LinkedList<>();
        for (Integer i = 0; i < 10; ++i) {
            listsToMerge.add(new ArrayList<>(Arrays.asList(i.toString())));
        }
        LinkedList<String> result = listsToMerge.stream().collect(instance);
        assertEquals(10, result.size());
    }

}
