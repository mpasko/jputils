/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.KanjiDictionary;
import org.mpasko.commons.analizers.ReadingDecomposer;
import org.mpasko.util.MapUtils;

/**
 *
 * @author marcin
 */
public class OnyomiSpeculationFilterTest {

    public OnyomiSpeculationFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testItemMatches() {
        System.out.println("itemMatches");
        final List<Map.Entry<String, String>> whitelistStub = Arrays.asList(
                MapUtils.entry("残", "ざん"),
                MapUtils.entry("酷", "こく"),
                MapUtils.entry("残", "のこす"));
        final KanjiDictionary kanjiDictionary = rewriteToKanjiDict(whitelistStub);
        kanjiDictionary.addItem("新", "", Arrays.asList("しん"), new LinkedList<>());
        final ReadingDecomposer decomposerStub = new ReadingDecomposer(kanjiDictionary);
        OnyomiSpeculationFilter instance = new OnyomiSpeculationFilter(whitelistStub, decomposerStub);
        assertEquals(true, instance.itemMatches(new DictEntry("残酷", "ざんこく", "")));
        assertEquals(false, instance.itemMatches(new DictEntry("新酷", "しんこく", "")));
    }

    @Test
    public void exceptionReproduction() {
        System.out.println("exceptionReproduction");
        final List<Map.Entry<String, String>> whitelistStub = Arrays.asList(
                MapUtils.entry("情", "じょう"),
                MapUtils.entry("報", "ほう"),
                MapUtils.entry("理", "り"),
                MapUtils.entry("論", "ろん"));
        final KanjiDictionary kanjiDictionary = rewriteToKanjiDict(whitelistStub);
        final ReadingDecomposer decomposerStub = new ReadingDecomposer(kanjiDictionary);
        OnyomiSpeculationFilter instance = new OnyomiSpeculationFilter(whitelistStub, decomposerStub);
        assertEquals(true, instance.itemMatches(new DictEntry("情報理論", "じょうほうりろん", "")));
    }

    private KanjiDictionary rewriteToKanjiDict(List<Map.Entry<String, String>> whitelistStub) {
        KanjiDictionary kanjiDictionary = new KanjiDictionary();
        whitelistStub.stream()
                .forEach(item -> kanjiDictionary.addItem(item.getKey(), "", Arrays.asList(item.getValue()), new LinkedList<>()));
        return kanjiDictionary;
    }
}
