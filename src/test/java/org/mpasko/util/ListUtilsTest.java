/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marcin
 */
public class ListUtilsTest {

    public ListUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testAreListsSame() {
        System.out.println("areListsSame");
        final List<String> list1 = Arrays.asList("ala", "ma", "kota");
        final List<String> list2 = Arrays.asList("ma", "ala", "kota");
        boolean result = ListUtils.areListsSame(list1, list2);
        assertEquals(true, result);
    }

}
