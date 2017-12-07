/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.mpasko.commons.Classifier;
import org.mpasko.commons.DictEntry;
import org.mpasko.loadres.dictionaryFileLoader.LineSplitter;
import org.mpasko.util.LangUtils;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class Dictionary {

    public static Dictionary loadTripleDict(String filename) {
        String content = Util.loadFile(filename);
        Dictionary dict = new Dictionary();
        LineSplitter splitter = new LineSplitter();
        for (String line : content.split("\n")) {
            String[] split = splitter.splitLine(line);
            dict.put(split[0], split[1], split[2]);
        }
        return dict;
    }

    private List<DictEntry> dict = new LinkedList<DictEntry>();
    private MultipleIndexer<DictEntry> strictindex;
    private MultipleIndexer<DictEntry> kanjiindex;
    private MultipleIndexer<DictEntry> stripindex;
    private MultipleIndexer<DictEntry> readindex;
    private static Comparator<? super DictEntry> THE_SHORTEST = (d1, d2) -> d1.writing.length() - d2.writing.length();

    public void put(String kanji, String writing, String english) {
        put(new DictEntry(kanji, writing, english));
    }

    public void put(String kanji, String english) {
        put(new DictEntry(kanji, english));
    }

    public void put(DictEntry item) {
        if (findStrict(item.kanji, item.writing) == null) {
            getDict().add(item);
            updateIndex(item);
        }
    }

    public List<DictEntry> items() {
        return new LinkedList(getDict());
    }

    public DictEntry find(String key, String value) {
        DictEntry hit = findStrict(key, value);
        if (hit == null) {
            hit = kanjiindex.getBest(key, THE_SHORTEST);
        }
        if (hit == null) {
            hit = readindex.getBest(value, THE_SHORTEST);
        }
        if (hit == null) {
            hit = stripindex.getBest(LangUtils.getOnlyPreInfix(key), THE_SHORTEST);
        }
        return hit;
    }

    public DictEntry findByReading(String value) {
        if (readindex == null) {
            createIndex();
        }
        return readindex.getBest(value, THE_SHORTEST);
    }

    private void createIndex() {
        strictindex = new MultipleIndexer<>();
        kanjiindex = new MultipleIndexer<>();
        stripindex = new MultipleIndexer<>();
        readindex = new MultipleIndexer<>();
        getDict().forEach((item) -> {
            if (Classifier.classify(item.kanji).containsJapanese()) {
                //we dont like Crisps anumore :(
                updateIndex(item);
            }
        });
    }

    public void addAll(Dictionary dict) {
        this.getDict().addAll(dict.items());
    }

    private void updateIndex(DictEntry item) {
        strictindex.put(item.kanji + item.writing, item);
        kanjiindex.put(item.kanji, item);
        stripindex.put(LangUtils.getOnlyPreInfix(item.kanji), item);
        readindex.put(item.writing, item);
    }

    public DictEntry findStrict(String key, String value) {
        if (strictindex == null) {
            createIndex();
        }
        DictEntry hit = strictindex.getBest(key + value, THE_SHORTEST);
        return hit;
    }

    public int size() {
        return this.getDict().size();
    }

    public void write(String filename) {
        Util.saveFile(filename, toString());
    }

    @Override
    public String toString() {
        StringBuilder all = new StringBuilder();
        for (DictEntry item : this.items()) {
            all.append(item.toString()).append("\n");
        }
        return all.toString();
    }

    /**
     * @return the dict
     */
    public List<DictEntry> getDict() {
        return dict;
    }

}
