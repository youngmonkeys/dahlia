package com.tvd12.dahlia.core.comparator;

import java.util.Comparator;

public final class NumberComparator implements Comparator<Number> {

	private static final NumberComparator INSTANCE = new NumberComparator();
	
	private NumberComparator() {}
	
	public static NumberComparator getInstance() {
		return INSTANCE;
	}
	
	@Override
	public int compare(Number o1, Number o2) {
		return Double.compare(o2.doubleValue(), o1.doubleValue());
	}
	
}
