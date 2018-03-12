/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpasko.util.collectors;

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
public class StringNewlineCollector implements Collector<String, StringBuilder, String> {

    public StringNewlineCollector() {
    }

    @Override
    public Supplier<StringBuilder> supplier() {
        return () -> new StringBuilder();
    }

    @Override
    public BiConsumer<StringBuilder, String> accumulator() {
        return (builder, item) -> builder.append(item).append("\n");
    }

    @Override
    public BinaryOperator<StringBuilder> combiner() {
        return (builder, another) -> builder.append(another.toString()).append("\n");
    }

    @Override
    public Function<StringBuilder, String> finisher() {
        return (builder) -> builder.toString();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return new TreeSet<>();
    }

}
