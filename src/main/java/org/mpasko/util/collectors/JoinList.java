/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util.collectors;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 *
 * @author marcin
 */
public class JoinList implements Collector<List<String>, LinkedList<String>, LinkedList<String>> {

    public JoinList() {
    }

    @Override
    public Supplier<LinkedList<String>> supplier() {
        return LinkedList::new;
    }

    @Override
    public BiConsumer<LinkedList<String>, List<String>> accumulator() {
        return (a, b) -> merge(a, b);
    }

    @Override
    public BinaryOperator<LinkedList<String>> combiner() {
        return (a, b) -> merge(a, b);
    }

    @Override
    public Function<LinkedList<String>, LinkedList<String>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return new TreeSet<>();
    }

    private LinkedList<String> merge(LinkedList<String> a, List<String> b) {
        a.addAll(b);
        return a;
    }
}
