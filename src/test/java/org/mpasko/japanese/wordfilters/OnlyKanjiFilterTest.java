/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;

/**
 *
 * @author marcin
 */
public class OnlyKanjiFilterTest {

    public OnlyKanjiFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testKatakanaFilter() {
        System.out.println("katakanaFilter");
        GenericFilter filter = OnlyKanjiFilter.katakanaFilter();
        final IDictionary filtered = filter.filter(mixedKatakanaAndKanji());
        assertEquals(3, filtered.size());
    }

    private Dictionary mixedKatakanaAndKanji() {
        final Dictionary dictionary = new Dictionary();
        dictionary.put("閉じ込む", "とじこむ", "to keep on file");
        dictionary.put("夢幻", "むげん", "ilusion");
        dictionary.put("リンゴ", "りんご", "apple");
        dictionary.put("リンゴ課", "りんごか", "applefruit");
        dictionary.put("だいじょうぶ", "だいじょうぶ", "alright");
        return dictionary;
    }
}
