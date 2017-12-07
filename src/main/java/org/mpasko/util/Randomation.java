/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class Randomation {
    public static <T> void shuffle(ArrayList<T> list) {
        single_shuffle(list);
        single_shuffle(list);
    }
    
    public static <T> void single_shuffle(ArrayList<T> list) {
        for (int base = 0; base<list.size()-2; ++base) {
            int target = new Random().nextInt(list.size()-1)+1;
            swap(list, base, target);
        }
    }
    public static <T> void swap(ArrayList<T> list, int base, int targ) {
        T tmp = list.get(base);
        list.set(base, (T) list.get(targ));
        list.set(targ, tmp);
    }

    public static <T> T choose(List<T> list) {
        int target = new Random().nextInt(list.size()-1)+1;
        return list.get(target);
    }
}
