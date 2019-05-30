/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.japanese.wordfilters;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.IDictionary;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author marcin
 */
public class NotEnglishOriginFilterTest {

    public NotEnglishOriginFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testKatakanaFilter() {
        System.out.println("NotEnglishFilter");
        GenericFilter filter = new NotEnglishOriginFilter();
        final IDictionary filtered = filter.filter(mixedKatakanaAndKanji());
        assertEquals(6, filtered.size());
    }

    private Dictionary mixedKatakanaAndKanji() {
        final Dictionary dictionary = new Dictionary();
        dictionary.put("閉じ込む", "とじこむ", "to keep on file");
        dictionary.put("夢幻", "むげん", "ilusion");
        dictionary.put("リンゴ", "りんご", "apple");
        dictionary.put("リンゴ課", "りんごか", "applefruit");
        dictionary.put("ヒヒ", "ひひ", "...");
        dictionary.put("コンコン", "こんこん", "knock knock");
        dictionary.put("ショウブショウブ", "しょうぶしょうぶ", "...");
        dictionary.put("だいじょうぶ", "だいじょうぶ", "alright");
        return dictionary;
    }
}
