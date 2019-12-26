package com.tvd12.dahlia.core.query;

import java.util.Map.Entry;
import java.util.function.Predicate;

import com.tvd12.ezyfox.entity.EzyObject;

public class QueryToPredicate {

	public Predicate<EzyObject> toPredicate(EzyObject query) {
		return toDefaultPredicate(query);
	}
	
	protected Predicate<EzyObject> toDefaultPredicate(EzyObject q) {
		for(Entry kv : q.entrySet()) {
			String field = (String) kv.getKey();
			Object qValue = kv.getValue();
			return record -> {
				Object rValue = record.get(field);
				return qValue.equals(rValue);
			};
		}
		return record -> false;
	}
	
	protected boolean isEquals(Object a, Object b) {
		if(a == null) {
			if(b == null)
				return true;
			return false;
		}
		else {
			if(b == null)
				return false;
			return a.equals(b);
		}
	}
	
	protected int compareValue(Object a, Object b) {
		if(a == null) {
			if(b == null)
				return 0;
			return -1;
		}
		else {
			if(b == null)
				return 1;
			return ((Comparable)a).compareTo(b);
		}
	}
	
}
