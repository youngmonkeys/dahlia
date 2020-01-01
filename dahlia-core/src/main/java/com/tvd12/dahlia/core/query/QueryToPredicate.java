package com.tvd12.dahlia.core.query;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

import com.tvd12.dahlia.core.comparator.Comparators;
import com.tvd12.dahlia.core.constant.Keywords;
import com.tvd12.dahlia.core.constant.Operation;
import com.tvd12.dahlia.core.exception.InvalidQueryException;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyPredicates;

public class QueryToPredicate {
	
	protected final Map<String, Function<Object, Predicate<EzyObject>>> builders;
	
	public QueryToPredicate() {
		this.builders = defaultBuilders();
	}

	public Predicate<EzyObject> toPredicate(EzyObject query) {
		try {
			return toPredicate0(query);
		}
		catch (Exception e) {
			throw new InvalidQueryException("invalid query: " + query, e);
		}
	}
	
	public Predicate<EzyObject> toPredicate0(EzyObject query) {
		for(Entry kv : query.entrySet()) {
			String op = (String)kv.getKey();
			Function<Object, Predicate<EzyObject>> builder = builders.get(op);
			if(builder != null)
				return builder.apply(kv.getValue());
			else
				return toDefaultPredicate(query);
		}
		return EzyPredicates.ALWAY_TRUE;
	}
	
	protected Predicate<EzyObject> toAndPredicate(EzyArray query) {
		return EzyPredicates.and(toPredicateList(query));
	}
	
	protected Predicate<EzyObject> toOrPredicate(EzyArray query) {
		return EzyPredicates.or(toPredicateList(query));
	} 
	
	protected List<Predicate> toPredicateList(EzyArray query) {
		List<Predicate> predicates = new ArrayList<>(query.size());
		for(int i = 0 ; i < query.size() ; ++i) {
			Object q = query.get(i);
			if(q instanceof EzyObject)
				predicates.add(toPredicate((EzyObject) q));
			else
				throw new InvalidQueryException("invalid query at: " + q);
		}
		return predicates;
	} 
	
	protected Predicate<EzyObject> toDefaultPredicate(EzyObject query) {
		for(Entry kv : query.entrySet()) {
			String field = null;
			Object value = kv.getValue();
			String fieldOrOperation = (String) kv.getKey();
			Operation operation = Operation.valueOfKeyword(fieldOrOperation);
			if(operation != null) {
				EzyObject oValue = (EzyObject)value;
				for(Entry e : oValue.entrySet()) {
					field = (String)e.getKey();
					value = e.getValue();
					break;
				}
			}
			else if(value instanceof EzyObject) {
				EzyObject oValue = (EzyObject)value;
				for(Entry e : oValue.entrySet()) {
					String keyword = (String)e.getKey();
					operation = Operation.valueOfKeyword(keyword);
					value = e.getValue();
					break;
				}
			}
			else {
				field = fieldOrOperation;
				operation = Operation.EQ;
			}
			String qField = field;
			Object qValue = value;
			Operation qOperation = operation;
			return record -> {
				Object rValue = record.get(qField);
				int compareResult = compareValue(rValue, qValue);
				if(qOperation == Operation.GT)
					return compareResult > 0;
				if(qOperation == Operation.GTE)
					return compareResult >= 0;
				if(qOperation == Operation.LT)
					return compareResult < 0;
				if(qOperation == Operation.LTE)
					return compareResult <= 0;
				return compareResult == 0;
			};
		}
		return record -> false;
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
			Comparators comparators = Comparators.getInstance();
			Comparator comparator = comparators.getComparator(a.getClass());
			int result = comparator != null 
					? comparator.compare(a, b) : ((Comparable)a).compareTo(b);
			return result;
		}
	}
	
	protected Map<String, Function<Object, Predicate<EzyObject>>> defaultBuilders() {
		Map<String, Function<Object, Predicate<EzyObject>>> map = new HashMap<>();
		map.put(Keywords.AND, q -> toAndPredicate((EzyArray) q));
		map.put(Keywords.OR, q -> toOrPredicate((EzyArray) q));
		return map;
	}
	
}
