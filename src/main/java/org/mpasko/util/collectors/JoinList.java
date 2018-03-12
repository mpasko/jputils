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
public class JoinList<T> implements Collector<List<T>, LinkedList<T>, LinkedList<T>> {

    public JoinList() {
    }

    @Override
    public Supplier<LinkedList<T>> supplier() {
        return LinkedList::new;
    }

    @Override
    public BiConsumer<LinkedList<T>, List<T>> accumulator() {
        return (a, b) -> merge(a, b);
    }

    @Override
    public BinaryOperator<LinkedList<T>> combiner() {
        return (a, b) -> merge(a, b);
    }

    @Override
    public Function<LinkedList<T>, LinkedList<T>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return new TreeSet<>();
    }

    private LinkedList<T> merge(LinkedList<T> a, List<T> b) {
        a.addAll(b);
        return a;
    }
}
