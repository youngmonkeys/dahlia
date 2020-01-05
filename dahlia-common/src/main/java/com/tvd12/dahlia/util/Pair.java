package com.tvd12.dahlia.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pair<K, V> {
	
	protected final K key;
	protected final V value;
	
	public Pair(K key) {
		this(key, null);
	}
	
}
