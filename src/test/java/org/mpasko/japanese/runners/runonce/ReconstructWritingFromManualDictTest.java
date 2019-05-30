/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.runners.runonce;

import java.util.List;
import java.util.Map.Entry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.DictionaryFileLoader;
import testutils.DictionaryRepository;

/**
 *
 * @author marcin
 */
public class ReconstructWritingFromManualDictTest {

    public ReconstructWritingFromManualDictTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testParseManualDict() {
        System.out.println("parseManualDict");
        String source = "kesseki-absence,calculus,blood stone";
        List<Entry<String, String>> result = DictionaryFileLoader.parseAsSimpleMap(source);
        Assert.assertEquals(3, result.size());
        for (Entry<String, String> entry : result) {
            Assert.assertEquals("kesseki", entry.getKey());
        }
    }

    @Test
    public void testParseManualDict_should_handle_spaces_correctly() {
        System.out.println("parseManualDict");
        String source = "kesseki - absence, calculus, blood stone";
        List<Entry<String, String>> result = DictionaryFileLoader.parseAsSimpleMap(source);
        Assert.assertEquals(3, result.size());
        for (Entry<String, String> entry : result) {
            Assert.assertEquals("kesseki", entry.getKey());
            Assert.assertEquals(entry.getValue(), entry.getValue().trim());
        }
    }

    @Test
    public void when_empty_definitinon_should_mark_as_empty() {
        System.out.println("parseManualDict");
        String source = "kesseki";
        List<Entry<String, String>> result = DictionaryFileLoader.parseAsSimpleMap(source);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("kesseki", result.get(0).getKey());
        Assert.assertEquals("", result.get(0).getValue());
    }

    @Test
    public void it_can_correctly_extract_furigana_words_from_dict() {
        StringBuilder builder = new StringBuilder();
        builder.append("ふかご-adjunct word,infallability").append("\n");
        builder.append("とうさく-perversion,inversion,plagiarism").append("\n");
        List<Entry<String, String>> manual = DictionaryFileLoader.parseAsSimpleMap(builder.toString());
        Dictionary sourceDict = DictionaryRepository.threeHomonymes();
        ReconstructWritingFromManualDict testable = new ReconstructWritingFromManualDict();
        List<DictEntry> result = testable.reconstructFrom(manual, sourceDict).items();
        Assert.assertEquals(4, result.size());
    }

    @Test
    public void it_can_correctly_extract_romaji_words_with_spaces_from_dict() {
        StringBuilder builder = new StringBuilder();
        builder.append("fukago - adjunct word, infallability").append("\n");
        builder.append("tousaku - perversion, inversion, plagiarism").append("\n");
        List<Entry<String, String>> manual = DictionaryFileLoader.parseAsSimpleMap(builder.toString());
        Dictionary sourceDict = DictionaryRepository.threeHomonymes();
        ReconstructWritingFromManualDict testable = new ReconstructWritingFromManualDict();
        List<DictEntry> result = testable.reconstructFrom(manual, sourceDict).items();
        Assert.assertEquals(4, result.size());
    }

}
