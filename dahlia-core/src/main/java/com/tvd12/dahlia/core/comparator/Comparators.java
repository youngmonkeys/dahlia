package com.tvd12.dahlia.core.comparator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public final class Comparators {
	
	private final Map<Class<?>, Comparator> comparators;
	private static final Comparators INSTANCE = new Comparators();
	
	private Comparators() {
		this.comparators = defaultComparators();
		
	}
	
	public static Comparators getInstance() {
		return INSTANCE;
	}
	
	public Comparator getComparator(Class<?> type) {
		Comparator comparator = comparators.get(type);
		return comparator;
	}
	
	private Map<Class<?>, Comparator> defaultComparators() {
		Map<Class<?>, Comparator> map = new HashMap<>();
		map.put(Byte.class, NumberComparator.getInstance());
		map.put(Float.class, NumberComparator.getInstance());
		map.put(Double.class, NumberComparator.getInstance());
		map.put(Integer.class, NumberComparator.getInstance());
		map.put(Long.class, NumberComparator.getInstance());
		map.put(Short.class, NumberComparator.getInstance());
		return map;
	}
	
}
