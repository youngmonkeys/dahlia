package com.tvd12.dahlia.util;

import lombok.Getter;

@Getter
public class Pair<K, V> {
	
	protected final K key;
	protected final V value;
	
	public Pair(K key) {
		this(key, null);
	}
	
	public Pair(K key, V value) {
		if(key == null)
			throw new NullPointerException("key can't be null");
		this.key = key;
		this.value = value;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj == this)
			return true;
		if(obj instanceof Pair)
			return key.equals(((Pair)obj).key);
		return false;
	}
	
	@Override
	public int hashCode() {
		return key.hashCode();
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("(")
					.append(key).append(":").append(value)
				.append(")")
				.toString();
	}
	
}
