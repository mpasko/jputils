/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.WritingChooser;

/**
 *
 * @author marcin
 */
public class DictionaryReconstructorTest {

    public DictionaryReconstructorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testReconstruct() {
        System.out.println("reconstruct");
        String source = generateSampleSource();
        Dictionary fullDictionary = generateSampleDictionary();
        DictionaryReconstructor instance = new DictionaryReconstructor(
                fullDictionary,
                new WritingChooser(),
                new MeaningChooser());
        Dictionary result = instance.reconstruct(source);
        assertEquals(6, result.size());
        final List<DictEntry> items = result.items();
        assertEquals("汪然", items.get(0).kanji);
        assertEquals("旺然", items.get(1).kanji);
        assertEquals("離陸", items.get(2).kanji);
        assertEquals("紛い", items.get(5).kanji);
    }

    @Test
    public void emptyLinesCornerCase() {
        System.out.println("emptyLinesCornerCase");
        String source = generateEmptyLines();
        Dictionary fullDictionary = generateSampleDictionary();
        DictionaryReconstructor instance = new DictionaryReconstructor(
                fullDictionary,
                new WritingChooser(),
                new MeaningChooser());
        Dictionary result = instance.reconstruct(source);
        assertEquals(0, result.size());
    }

    private String generateSampleSource() {
        String source = "おうぜん-vigorously flowing\n"
                + "おうぜん-prosperous\n"
                + "りりく-takeoff\n"
                + "ひろがえる-flutter\n"
                + "しのびよる-steal up\n"
                + "まがい-imitation,sham";
        return source;
    }

    private String generateEmptyLines() {
        String source = "\n"
                + "-\n"
                + " \n"
                + "  \n"
                + "- \n"
                + " -\n"
                + " - \n"
                + "";
        return source;
    }

    public static Dictionary generateSampleDictionary() {
        Dictionary fullDictionary = new Dictionary();
        fullDictionary.put("汪然", "おうぜん", "vigorously flowing");
        fullDictionary.put("旺然", "おうぜん", "prosperous");
        fullDictionary.put("離陸", "りりく", "takeoff");
        fullDictionary.put("真ほら", "まほら", "great and splendid land,excellent location");
        fullDictionary.put("拗れる", "こじれる", "to get complicated, grow worse, turn sour");
        fullDictionary.put("翻る", "ひろがえる", "to flutter, to wave, to flap");
        fullDictionary.put("忍び寄る", "しのびよる", "to creep, to steal up, to draw near");
        fullDictionary.put("紛い", "まがい", "imitation,sham,-like");
        return fullDictionary;
    }

}
