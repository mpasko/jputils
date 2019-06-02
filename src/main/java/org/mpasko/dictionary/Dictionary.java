/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.commons.Furiganiser;
import org.mpasko.dictionary.formatters.DictionaryFormatter;
import org.mpasko.dictionary.formatters.IFeatureChooser;
import org.mpasko.dictionary.formatters.KanjiChooser;
import org.mpasko.dictionary.formatters.WritingChooser;
import org.mpasko.util.Filesystem;
import org.mpasko.util.ImmutableList;
import org.mpasko.util.LangUtils;
import org.mpasko.util.StringUtils;
import org.mpasko.util.collectors.DictEntryCollector;

/**
 *
 * @author marcin
 */
public class Dictionary extends AbstractDictionary {

    private MultipleIndexer<DictEntry> strictindex;
    private MultipleIndexer<DictEntry> kanjiindex;
    private MultipleIndexer<DictEntry> stripindex;
    private Map<String, UniversalIndex> indexByFeature = new HashMap<String, UniversalIndex>();
    private UniversalIndex readindex = new UniversalIndex(new WritingChooser(), this);
    private static Comparator<? super DictEntry> THE_SHORTEST = (d1, d2) -> d1.writing.length() - d2.writing.length();

    public Dictionary(List<DictEntry> initializeWith) {
        this();
        this.putAll(new LinkedList<>(initializeWith));
    }

    public Dictionary() {
        dict = new LinkedList<>();
    }

    public Dictionary(IDictionary iDictionary) {
        dict = iDictionary.items();
    }

    @Override
    public void put(DictEntry item) {
        dict.add(item);
        updateIndex(item);
    }

    @Override
    public void putAll(List<DictEntry> found) {
        found.stream().forEach(this::put);
    }

    @Override
    public DictEntry find(String key, String value) {
        DictEntry hit = findStrict(key, value);
        if (hit == null) {
            hit = kanjiindex.getBest(key, THE_SHORTEST);
        }
        if (hit == null) {
            hit = readindex.findBest(new DictEntry(key, value, ""));
        }
        if (hit == null) {
            hit = stripindex.getBest(LangUtils.getOnlyPreInfix(key), THE_SHORTEST);
        }
        return hit;
    }

    public DictEntry findByReading(String value) {
        return readindex.findBest(new DictEntry("", value, ""));
    }

    public LinkedList<DictEntry> findAllByFeature(String value, IFeatureChooser feature) {
        UniversalIndex index = createIndexIfNotExist(feature);
        return index.findAll(value);
    }

    public LinkedList<DictEntry> findAllByReading(String value) {
        String furigana = new Furiganiser().furiganise(value);
        return readindex.findAll(new DictEntry("", furigana, ""));
    }

    public LinkedList<DictEntry> findPhoneticWithMeaning(String key, String value) {
        LinkedList<DictEntry> foundPhonetical = findAllByReading(key);
        return foundPhonetical
                .stream()
                .filter(entry -> containsNormalized(entry.english, value))
                .collect(new DictEntryCollector());
    }

    public LinkedList<DictEntry> findKeysAndValuesContaining(String key, String value, IFeatureChooser keyChooser, IFeatureChooser valueChooser) {
        LinkedList<DictEntry> foundExact = findAllByFeature(key, keyChooser);
        return foundExact
                .stream()
                .filter(entry -> containsNormalized(valueChooser.choose(entry), value))
                .collect(new DictEntryCollector());
    }

    public DictEntry findShortestByFeature(String value, KanjiChooser feature) {
        UniversalIndex index = createIndexIfNotExist(feature);
        return index.findBest(value, UniversalIndex.THE_SHORTEST_WRITING);
    }

    private boolean containsNormalized(String bigger, String smaller) {
        final String smallerNormalized = StringUtils.clear(smaller);
        final String biggerNormalized = StringUtils.clear(bigger);
        return biggerNormalized.contains(smallerNormalized);
    }

    private void createIndex() {
        strictindex = new MultipleIndexer<>();
        kanjiindex = new MultipleIndexer<>();
        stripindex = new MultipleIndexer<>();
        dict.forEach((item) -> {
            if (Classifier.classify(item.kanji).containsJapanese()) {
                //we dont like Crisps anumore :(
                strictindex.put(item.kanji + item.writing, item);
                kanjiindex.put(item.kanji, item);
                stripindex.put(LangUtils.getOnlyPreInfix(item.kanji), item);
            }
        });
        readindex.createIndex();
    }

    private void updateIndex(DictEntry item) {
        if (strictindex == null) {
            createIndex();
        }
        strictindex.put(item.kanji + item.writing, item);
        kanjiindex.put(item.kanji, item);
        stripindex.put(LangUtils.getOnlyPreInfix(item.kanji), item);
        readindex.updateIndex(item);
    }

    @Override
    public DictEntry findStrict(String key, String value) {
        if (strictindex == null) {
            createIndex();
        }
        DictEntry hit = strictindex.getBest(key + value, THE_SHORTEST);
        return hit;
    }

    @Override
    public DictEntry findDefault(String kanji) {
        if (strictindex == null) {
            createIndex();
        }
        return kanjiindex.getBest(kanji, THE_SHORTEST);
    }

    private UniversalIndex createIndexIfNotExist(IFeatureChooser feature) {
        String featureName = feature.getClass().getName();
        UniversalIndex result = indexByFeature.get(featureName);
        if (result == null) {
            result = new UniversalIndex(feature, this);
            indexByFeature.put(featureName, result);
        }
        return result;
    }

}
