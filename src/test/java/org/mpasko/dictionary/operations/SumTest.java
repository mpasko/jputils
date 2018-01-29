/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary.operations;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.dictionary.Dictionary;
import static org.mpasko.dictionary.operations.ProductTest.generateSampleDictionary1;
import static org.mpasko.dictionary.operations.ProductTest.generateSampleDictionary2;
import testutils.DictionaryComparer;

/**
 *
 * @author marcin
 */
public class SumTest {

    public SumTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testResult() {
        System.out.println("result");
        Sum instance = new Sum(generateSampleDictionary1(), generateSampleDictionary2());
        Dictionary result = instance.result();
        Dictionary expectedResult = generateTemplateSum();
        DictionaryComparer.assertSame(result, expectedResult);
    }

    public static Dictionary generateTemplateSum() {
        Dictionary fullDictionary = new Dictionary();
        fullDictionary.put("汪然", "おうぜん", "vigorously flowing");
        fullDictionary.put("真ほら", "まほら", "great and splendid land,excellent location");
        fullDictionary.put("旺然", "おうぜん", "prosperous");
        fullDictionary.put("拗れる", "こじれる", "to get complicated, grow worse, turn sour");
        fullDictionary.put("離陸", "りりく", "takeoff");
        fullDictionary.put("忍び寄る", "しのびよる", "to creep, to steal up, to draw near");
        fullDictionary.put("紛い", "まがい", "imitation,sham,-like");
        fullDictionary.put("翻る", "ひろがえる", "to flutter, to wave, to flap");
        return fullDictionary;
    }
}
