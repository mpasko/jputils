/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.loadres;

import java.util.Arrays;
import java.util.List;
import org.mpasko.dictionary.Dictionary;
import org.mpasko.util.FormatterUtil;

/**
 *
 * @author marcin
 */
public class PopularDictionaries {

    public static Dictionary loadGoetheDictionaries() {
        Dictionary mainDict = new Dictionary();
        for (int i = 1; i <= 42; ++i) {
            String alignedNumber = FormatterUtil.alignString(2, "0", String.valueOf(i));
            String filename = String.format("dictionaries/goethe/goethe%s.txt", alignedNumber);
            System.out.println(filename);
            Dictionary dict = Dictionary.loadTripleDict(filename);
            mainDict.addAll(dict);
        }
        return mainDict;
    }

    public static Dictionary loadFieldDictionaries() {
        Dictionary mainDict = new Dictionary();
        List<String> fields = Arrays.asList("math", "anat", "chem", "geom", "med", "physics", "sports");
        for (String field : fields) {
            String filename = String.format("dictionaries/fields/%s.txt", field);
            System.out.println(filename);
            Dictionary dict = Dictionary.loadTripleDict(filename);
            mainDict.addAll(dict);
        }
        return mainDict;
    }
}
