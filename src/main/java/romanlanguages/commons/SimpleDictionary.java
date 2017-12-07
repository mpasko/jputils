/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package romanlanguages.commons;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import org.mpasko.util.LangUtils;
import org.mpasko.util.Util;

/**
 *
 * @author marcin
 */
public class SimpleDictionary {

    public static SimpleDictionary loadDict(String filename) {
        String content = Util.loadFile(filename);
        SimpleDictionary dict = new SimpleDictionary();
        for (String line : content.split("\n")) {
            String[] byDash = line.split("-");
            dict.put(byDash[0], byDash[1]);
        }
        return dict;
    }
    List<SimpleEntry> dict = new LinkedList<SimpleEntry>();
    private TreeMap<String, SimpleEntry> strictindex;
    private TreeMap<String, SimpleEntry> index;

    public List<SimpleEntry> items() {
        return new LinkedList(dict);
    }
    
    public void putAll(SimpleDictionary dict2) {
        for (SimpleEntry item : dict2.dict) {
            this.put(item);
        }
    }
    
    public void put(String kanji, String english) {
        put(new SimpleEntry(kanji, english));
    }

    public void put(SimpleEntry item) {
        if (findStrict(item.word, item.translation) == null) {
            dict.add(item);
            updateIndex(item);
        }
    }

    private void createIndex() {
        strictindex = new TreeMap<String, SimpleEntry>();
        index = new TreeMap<String, SimpleEntry>();
        for (SimpleEntry item : dict) {
            updateIndex( item);
        }
    }
    
    public void updateIndex(SimpleEntry item) {
        strictindex.put(item.word + item.translation, item);
        index.put(item.word, item);
    }

    public SimpleEntry findStrict(String key, String value) {
        if (strictindex == null) {
            createIndex();
        }
        SimpleEntry hit = strictindex.get(key + value);
        return hit;
    }

    public int size() {
        return this.dict.size();
    }
    
    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        for (SimpleEntry item : dict) {
            b.append(item.word).append("-").append(item.translation).append("\n");
        }
        return b.toString();
    }
}
