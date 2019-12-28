package com.tvd12.dahlia.core.comparator;

import java.util.Comparator;

public final class LongComparator implements Comparator<Number> {

	private static final LongComparator INSTANCE = new LongComparator();
	
	private LongComparator() {}
	
	public static LongComparator getInstance() {
		return INSTANCE;
	}
	
	@Override
	public int compare(Number o1, Number o2) {
		long longValue1 = o1.longValue();
		long longValue2 = o2.longValue();
		double doubleValue2 = o2.doubleValue();
		if(longValue2 > doubleValue2)
			return 1;
		if(longValue2 < doubleValue2)
			return -1;
		return Long.compare(longValue1, longValue2); 
	}
	
}
