/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.loadres.dictionaryFileLoader;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marcin
 */
public class LineSplitterTest {

    public LineSplitterTest() {
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
     * Test of splitLine method, of class LineSplitter.
     */
    @Test
    public void testSplitLine() {
        System.out.println("splitLine");
        testGeneralCase("key-value", "key", "", "value");
        testGeneralCase("key-explain-value", "key", "explain", "value");
        testGeneralCase("key explain-value", "key", "explain", "value");
        testGeneralCase("key explain - value", "key", "explain", "value");
    }

    @Test
    public void testSplitLineRegression() {
        System.out.println("testSplitLineRegression");
        testGeneralCase(" ", "", "", "");
        testGeneralCase("", "", "", "");
        testGeneralCase("-", "", "", "");
    }

    private void testGeneralCase(String line, String... expectations) {
        System.out.println(String.format("case: %s", line));
        LineSplitter instance = new LineSplitter();
        String[] result = instance.splitLine(line);
        Assert.assertEquals("expected first word to split correctly", expectations[0], result[0]);
        Assert.assertEquals("expected second word to split correctly", expectations[1], result[1]);
        Assert.assertEquals("expected last word to split correctly", expectations[2], result[2]);
    }
}
