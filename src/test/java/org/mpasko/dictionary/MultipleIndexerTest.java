/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author marcin
 */
public class MultipleIndexerTest {

    public MultipleIndexerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    /**
     * Test of getBest method, of class MultipleIndexer.
     */
    @Test
    public void when_the_shortest_comparator_then_should_give_the_shortest_value() {
        MultipleIndexer<String> indexer = new MultipleIndexer<>();
        final String some_key = "some_key";
        indexer.put(some_key, "alalalala");
        indexer.put(some_key, "alalala");
        indexer.put(some_key, "alala");
        indexer.put(some_key, "alalalala");
        String result = indexer.getBest(some_key, MultipleIndexer.THE_SHORTEST);
        Assert.assertEquals("alala", result);
    }

    @Test
    public void when_no_value_then_should_give_null() {
        MultipleIndexer<String> indexer = new MultipleIndexer<>();
        final String some_key = "some_key";
        String result = indexer.getBest(some_key, MultipleIndexer.THE_SHORTEST);
        Assert.assertEquals(null, result);
    }

}
