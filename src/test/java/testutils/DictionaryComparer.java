/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testutils;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.dictionary.UniversalIndex;
import org.mpasko.dictionary.formatters.complex.StandardFormatter;

/**
 *
 * @author marcin
 */
public class DictionaryComparer {

    public static void assertSame(Dictionary dict1, Dictionary dict2) {
        assertEquals("Dictionary sizes do not match", dict1.size(), dict2.size());
        UniversalIndex index = new UniversalIndex(new StandardFormatter(), dict1);
        for (DictEntry entry : dict2.items()) {
            final String message = "Expected first dictionary to contain: " + entry.toString();
            Assert.assertNotNull(message, index.findBest(entry));
        }
    }
}
