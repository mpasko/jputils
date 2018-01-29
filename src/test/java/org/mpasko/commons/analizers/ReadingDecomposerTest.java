/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.commons.analizers;

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

/**
 *
 * @author marcin
 */
public class ReadingDecomposerTest {

    public ReadingDecomposerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testDecompose() {
        System.out.println("decompose");
        KanjiDictionary kanjiDict = new KanjiDictionary();
        testOnyomi(kanjiDict, "艦", "カン", "長", "チョウ", "艦長", "かんちょう");
        testOnyomi(kanjiDict, "徹", "テツ", "底", "テイ", "徹底", "てってい");
        testKunyomi(kanjiDict, "研", "と.ぎ", "澄", "す.ます", "研ぎ澄ます", "とぎすます");
    }

    private void testOnyomi(KanjiDictionary kanjiDict, final String kanji1, final String reading1, final String kanji2, final String reading2, final String wordKanji, final String wordreading) {
        kanjiDict.addItem(kanji1, "", Arrays.asList(reading1), new LinkedList<>());
        kanjiDict.addItem(kanji2, "", Arrays.asList(reading2), new LinkedList<>());
        ReadingDecomposer instance = new ReadingDecomposer(kanjiDict);
        List<Map.Entry<String, String>> result = instance.decompose(new DictEntry(wordKanji, wordreading, ""));
        assertEquals(2, result.size());
        assertEquals(kanji1, result.get(0).getKey());
        assertEquals(reading1, result.get(0).getValue());
        assertEquals(kanji2, result.get(1).getKey());
        assertEquals(reading2, result.get(1).getValue());
    }

    private void testKunyomi(KanjiDictionary kanjiDict, final String kanji1, final String reading1, final String kanji2, final String reading2, final String wordKanji, final String wordreading) {
        kanjiDict.addItem(kanji1, "", new LinkedList<>(), Arrays.asList(reading1));
        kanjiDict.addItem(kanji2, "", new LinkedList<>(), Arrays.asList(reading2));
        ReadingDecomposer instance = new ReadingDecomposer(kanjiDict);
        List<Map.Entry<String, String>> result = instance.decompose(new DictEntry(wordKanji, wordreading, ""));
        assertEquals(2, result.size());
        assertEquals(kanji1, result.get(0).getKey());
        assertEquals(ReadingDecomposer.removeDots(reading1), result.get(0).getValue());
        assertEquals(kanji2, result.get(1).getKey());
        assertEquals(ReadingDecomposer.removeDots(reading2), result.get(1).getValue());
    }
}
