/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author marcin
 */
public class MapList<Key, T> {

    private Map<Key, LinkedList<T>> map = new HashMap<Key, LinkedList<T>>();

    public LinkedList<T> get(Key key) {
        insertEmptyIfNull(key);
        return map.get(key);
    }

    public void add(Key key, T value) {
        insertEmptyIfNull(key);
        map.get(key).add(value);
    }

    private void insertEmptyIfNull(Key key) {
        if (map.get(key) == null) {
            map.put(key, new LinkedList<T>());
        }
    }

    public Iterable<Key> keySet() {
        return map.keySet();
    }
}
