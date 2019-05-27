/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.LinkedList;
import org.mpasko.commons.DictEntry;
import org.mpasko.dictionary.formatters.IFeatureChooser;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.MeaningChooser;
import org.mpasko.dictionary.formatters.RomajiWritingChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.util.Filesystem;

/**
 *
 * @author marcin
 */
public class DictionaryReconstructor {

    private final Dictionary fullDictionary;
    private final IFeatureChooser containingFeature;
    private final IFeatureChooser exactFeature;

    public DictionaryReconstructor(Dictionary fullDictionary,
            IFeatureChooser keyFeature,
            IFeatureChooser valueFeature) {
        this.fullDictionary = fullDictionary;
        this.exactFeature = keyFeature;
        this.containingFeature = valueFeature;
    }

    public static DictionaryReconstructor StandardListeningReconstructor(Dictionary fullDictionary) {
        return new DictionaryReconstructor(fullDictionary,
                new WritingChooser(),
                new MeaningChooser());
    }

    public static DictionaryReconstructor StandardReadingReconstructor(Dictionary fullDictionary) {
        return new DictionaryReconstructor(fullDictionary,
                new KanjiChooser(),
                new RomajiWritingChooser());
    }

    public IDictionary reconstructFromFile(String filename) {
        return reconstruct(new Filesystem().loadFile(filename));
    }

    public Dictionary reconstruct(String source) {
        final Dictionary resultDic = new Dictionary();
        String[] lines = source.split("\n");
        for (String line : lines) {
            resultDic.putAll(reconstructLine(line));
        }
        return resultDic;
    }

    private LinkedList<DictEntry> reconstructLine(String line) {
        final String[] splitted = line.split("-");
        if (splitted.length == 0) {
            return new LinkedList<>();
        }
        String key = splitted[0];
        if (splitted.length == 1) {
            return fullDictionary.findAllByFeature(key, exactFeature);
        }
        String value = splitted[1];
        return fullDictionary.findKeysAndValuesContaining(key, value, exactFeature, containingFeature);
    }
}
