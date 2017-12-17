/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util;

import java.util.List;

/**
 *
 * @author marcin
 */
public class ListUtils {

    public static <A> boolean areListsSame(List<A> first, List<A> second) {
        boolean lengthMatch = first.size() == second.size();
        return lengthMatch && isContainedIn(first, second);
    }

    private static <A> boolean isContainedIn(List<A> first, List<A> second) {
        boolean firstIsContainedInSecond = first.stream().allMatch(one -> second.contains(one));
        return firstIsContainedInSecond;
    }
}
