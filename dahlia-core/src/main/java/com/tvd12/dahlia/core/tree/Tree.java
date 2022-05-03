package com.tvd12.dahlia.core.tree;

import com.tvd12.dahlia.math.Operation;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("unchecked")
public abstract class Tree<K, V> implements Map<K, V> {

    protected final Comparator<K> keyComparator;

    public Tree() {
        this(null);
    }

    // ======================= constructor ============
    public Tree(Comparator<K> keyComparator) {
        this.keyComparator = keyComparator;
    }

    // ======================= tree method ============
    public abstract void insert(K key, V value);

    public abstract V delete(K key);

    public abstract Entry<K, V> search(K key);

    public abstract Entry<K, V> search(K key, Operation op);

    public abstract void walk(TreeWalker<K, V> walker);

    public abstract void walkReverse(TreeWalker<K, V> walker);

    // ======================= map method =============
    @Override
    public V get(Object key) {
        Entry<K, V> e = search((K) key);
        return e != null ? e.getValue() : null;
    }

    @Override
    public V put(K key, V value) {
        insert(key, value);
        return null;
    }

    @Override
    public V remove(Object key) {
        return delete((K) key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            V value = m.get(key);
            insert(key, value);
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        walk(e -> set.add(e.getKey()));
        return set;
    }

    @Override
    public Collection<V> values() {
        Collection<V> list = new ArrayList<>();
        walk(e -> list.add(e.getValue()));
        return list;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        walk(set::add);
        return set;
    }

    @Override
    public int size() {
        AtomicInteger size = new AtomicInteger();
        walk(e -> size.incrementAndGet());
        return size.get();
    }

    public long sizeLong() {
        AtomicLong size = new AtomicLong();
        walk(e -> size.incrementAndGet());
        return size.get();
    }

    @Override
    public boolean isEmpty() {
        AtomicBoolean empty = new AtomicBoolean(true);
        walk(new TreeWalker<K, V>() {
            @Override
            public void accept(Entry<K, V> entry) {
                empty.set(false);
            }

            @Override
            public boolean next() {
                return empty.get();
            }
        });
        return empty.get();
    }

    @Override
    public boolean containsKey(Object key) {
        Entry<K, V> e = search((K) key);
        return e != null;
    }

    @Override
    public boolean containsValue(Object value) {
        AtomicBoolean found = new AtomicBoolean(false);
        walk(new TreeWalker<K, V>() {
            @Override
            public void accept(Entry<K, V> entry) {
                V v = entry.getValue();
                if (v == null) {
                    if (value == null) {
                        found.set(true);
                    }

                } else if (v.equals(value)) {
                    found.set(true);
                }
            }

            @Override
            public boolean next() {
                return !found.get();
            }
        });
        return found.get();
    }

    protected int compareKey(K a, K b) {
        int result;
        if (keyComparator != null) {
            result = keyComparator.compare(a, b);
        } else {
            if (a instanceof Comparable) {
                result = ((Comparable<K>) a).compareTo(b);
            } else {
                throw new IllegalArgumentException("key must implement Comparable");
            }
        }
        return result;
    }

    protected int compareEntryKey(Entry<K, V> entry, K key) {
        return compareKey(entry.getKey(), key);
    }

    protected int compareEntry(Entry<K, V> a, Entry<K, V> b) {
        return compareKey(a.getKey(), b.getKey());
    }

    @Getter
    public static class Entry<K, V> implements Map.Entry<K, V> {

        protected final K key;
        protected V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }

        @Override
        public String toString() {
            return key.toString();
        }

    }

}
