/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters.wordsplitter;

import org.mpasko.parseTexts.DictionarySplitter;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import testutils.DictionaryComparer;

/**
 *
 * @author marcin
 */
public class DictionarySplitterTest {

    public DictionarySplitterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testSplit() {
        System.out.println("split");
        testSplit("三角行列", "さんかくぎょうれつ", "triangular matrix", getTriangularMatrixSplits());
        testSplit("三角行列的", "さんかくぎょうれつてき", "triangular matrixish", getTriangularMatrixishSplits());
        testSplit("三角形行列", "さんかくけいぎょうれつ", "triangular matrixish", getTriangularLikeMatrixSplits());
        testSplit("項目応答理論", "こうもくおうとうりろん", "item response theory,IRT", getItemResponseSplits());
        testCannotSplit("項目応答理論馬鹿話", "こうもくおうとうりろんばかばんあし", "blablabla");
        testCannotSplit("三角行列馬鹿", "さんかくぎょうれつばか", "blablabla");
    }

    private void testSplit(final String kanji, final String writing, final String meaning, final Dictionary triangularMatrixSplits) {
        DictionarySplitter instance = new DictionarySplitter(getSplitableDictionary(), "", "");
        List<DictEntry> result = instance.split(new DictEntry(kanji, writing, meaning));
        DictionaryComparer.assertSame(triangularMatrixSplits, new Dictionary(result));
    }

    public static Dictionary getSplitableDictionary() {
        final Dictionary dictionary = new Dictionary();
        dictionary.putAll(getTriangularMatrixSplits().items());
        dictionary.putAll(getItemResponseSplits().items());
        dictionary.put("行列的", "ぎょうれつてき", "matrixish");
        dictionary.put("三角形", "さんかくけい", "triangular-like");
        return dictionary;
    }

    public static Dictionary getTriangularMatrixSplits() {
        final Dictionary dictionary = new Dictionary();
        dictionary.put("三角", "さんかく", "triangle");
        dictionary.put("行列", "ぎょうれつ", "matrix");
        return dictionary;
    }

    public static Dictionary getTriangularMatrixishSplits() {
        final Dictionary dictionary = new Dictionary();
        dictionary.put("三角", "さんかく", "triangle");
        dictionary.put("行列的", "ぎょうれつてき", "matrixish");
        return dictionary;
    }

    private Dictionary getTriangularLikeMatrixSplits() {
        final Dictionary dictionary = new Dictionary();
        dictionary.put("三角形", "さんかくけい", "triangular-like");
        dictionary.put("行列", "ぎょうれつ", "matrix");
        return dictionary;
    }

    public static Dictionary getItemResponseSplits() {
        final Dictionary dictionary = new Dictionary();
        dictionary.put("項目", "こうもく", "item");
        dictionary.put("応答", "おうとう", "response");
        dictionary.put("理論", "りろん", "theory");
        return dictionary;
    }

    private void testCannotSplit(String kanji, String writing, String meaning) {
        DictionarySplitter instance = new DictionarySplitter(getSplitableDictionary(), "", "");
        List<DictEntry> result = instance.split(new DictEntry(kanji, writing, meaning));
        assertEquals(1, result.size());
        assertEquals(kanji, result.get(0).kanji);
    }
}
