package org.mpasko.util;

import com.sun.xml.internal.fastinfoset.stax.events.ReadIterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableList<T> implements List<T> {
    List<T> target;

    public ImmutableList(List<T> from) {
        this.target = from;
    }

    @Override
    public int size() {
        return target.size();
    }

    @Override
    public boolean isEmpty() {
        return target.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return target.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return target.iterator();
    }

    @Override
    public Object[] toArray() {
        return target.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return target.toArray(a);
    }

    @Override
    public boolean add(T t) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public boolean remove(Object o) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return target.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public void clear() {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public T get(int index) {
        return target.get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public void add(int index, T element) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public T remove(int index) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public int indexOf(Object o) {
        return target.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return target.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException("This is an immutable collection!");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return target.subList(fromIndex, toIndex);
    }
}
