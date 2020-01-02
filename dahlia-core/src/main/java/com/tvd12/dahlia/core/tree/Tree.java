package com.tvd12.dahlia.core.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.Getter;

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
	public abstract void walk(TreeWalker<K, V> walker);
	public abstract void walkReverse(TreeWalker<K, V> walker);

	// ======================= map method =============
	@Override
	public V get(Object key) {
		Entry<K, V> e = search((K)key);
		return e != null ? e.getValue() : null;
	}

	@Override
	public V put(K key, V value) {
		insert(key, value);
		return value;
	}

	@Override
	public V remove(Object key) {
		V value = delete((K)key);
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for(K key : m.keySet()) {
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
		walk(e -> set.add(e));
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
		Entry<K, V> e = search((K)key);
		return e != null;
	}

	@Override
	public boolean containsValue(Object value) {
		AtomicBoolean found = new AtomicBoolean(false);
		walk(new TreeWalker<K, V>() {
			@Override
			public void accept(Entry<K, V> entry) {
				V v = entry.getValue();
				if(v == null) {
					if(value == null)
						found.set(true);
					
				}
				else if(v.equals(value)) {
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
		int result = 0;
		if(keyComparator != null) {
			result = keyComparator.compare(a, b);
		}
		else {
			if(a instanceof Comparable)
				result = ((Comparable<K>) a).compareTo(b);
			else
				throw new IllegalArgumentException("key must implement Comparable");
		}
		return result;
	}
	
	protected int compareEntryKey(Entry<K, V> entry, K key) {
		int result = compareKey(entry.getKey(), key);
		return result;
	}
	
	protected int compareEntry(Entry<K, V> a, Entry<K, V> b) {
		int result = compareKey(a.getKey(), b.getKey());
		return result;
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
			V old = value;
			this.value = value;
			return old;
		}
		
		@Override
		public String toString() {
			return key.toString();
		}
		
	}
	
}
