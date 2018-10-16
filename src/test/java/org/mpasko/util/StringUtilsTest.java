/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marcin
 */
public class StringUtilsTest {

    public StringUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testJoinPath() {
        System.out.println("joinPath");
        String result = StringUtils.joinPath("node1", "node2\\", "node3\\", "\\node4");
        assertEquals("node1/node2/node3/node4", result);
        String result2 = StringUtils.joinPath("node1", "node2/", "node3/", "/node4");
        assertEquals("node1/node2/node3/node4", result2);
    }

    @Test
    public void testAlignPath() {
        System.out.println("slignPath");
        alignPathSuite("node1/node2/node3/", "/node2/node3/node4.txt", "node1/node2/node3/node4.txt");
        alignPathSuite("node1/node2/node3", "/node2/node3/node4.txt", "node1/node2/node3/node4.txt");
        alignPathSuite("node1/node2/node3/", "node2/node3/node4.txt", "node1/node2/node3/node4.txt");
        alignPathSuite("node1/node2", "node1/node2/node3.txt", "node1/node2/node3.txt");
    }

    private void alignPathSuite(String base, String tip, String result) {
        assertEquals(result, StringUtils.alignPaths(base, tip));
    }
}
