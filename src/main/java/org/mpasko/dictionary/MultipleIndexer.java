/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.mpasko.util.MapList;

/**
 *
 * @author marcin
 */
public class MultipleIndexer<T> {

    static Comparator<? super String> THE_SHORTEST = (s1, s2) -> s1.length() - s2.length();
    static Comparator<? super String> THE_LONGEST = (s1, s2) -> s2.length() - s1.length();

    private MapList<String, T> map = new MapList<String, T>();

    public void put(String key, T item) {
        map.add(key, item);
    }

    public T getBest(String key, Comparator<? super T> comparator) {
        List<T> list = getAll(key);
        if (list.isEmpty()) {
            return null;
        } else {
            ArrayList<T> sortable = new ArrayList<>(list);
            Collections.sort(sortable, comparator);
            return sortable.get(0);
        }
    }

    public LinkedList<T> getAll(String key) {
        return map.get(key);
    }
}
