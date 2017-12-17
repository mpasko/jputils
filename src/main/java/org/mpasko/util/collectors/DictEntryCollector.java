/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util.collectors;

import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.mpasko.commons.DictEntry;

/**
 *
 * @author marcin
 */
public class DictEntryCollector implements Collector<DictEntry, LinkedList<DictEntry>, LinkedList<DictEntry>> {

    @Override
    public Supplier<LinkedList<DictEntry>> supplier() {
        return () -> new LinkedList<>();
    }

    @Override
    public BiConsumer<LinkedList<DictEntry>, DictEntry> accumulator() {
        return (list, item) -> list.add(item);
    }

    @Override
    public BinaryOperator<LinkedList<DictEntry>> combiner() {
        return this::combineTwo;
    }

    private LinkedList<DictEntry> combineTwo(LinkedList<DictEntry> hungryList, LinkedList<DictEntry> devoured) {
        hungryList.addAll(devoured);
        return hungryList;
    }

    @Override
    public Function<LinkedList<DictEntry>, LinkedList<DictEntry>> finisher() {
        return alReady -> alReady;
    }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return new TreeSet<>();
    }
}
