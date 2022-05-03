package com.tvd12.dahlia.core.query;

import com.tvd12.dahlia.constant.Keywords;
import com.tvd12.dahlia.core.comparator.Comparators;
import com.tvd12.dahlia.exception.InvalidQueryException;
import com.tvd12.dahlia.math.Operation;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyPredicates;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public class QueryToPredicate {

    protected final Map<String, Function<Object, Predicate<EzyObject>>> builders;

    public QueryToPredicate() {
        this.builders = defaultBuilders();
    }

    public Predicate<EzyObject> toPredicate(EzyObject query) {
        try {
            return toPredicate0(query);
        } catch (Exception e) {
            throw new InvalidQueryException("invalid query: " + query, e);
        }
    }

    public Predicate<EzyObject> toPredicate0(EzyObject query) {
        for (Entry kv : query.entrySet()) {
            String op = (String) kv.getKey();
            Function<Object, Predicate<EzyObject>> builder = builders.get(op);
            if (builder != null) {
                return builder.apply(kv.getValue());
            } else {
                return toDefaultPredicate(query);
            }
        }
        return EzyPredicates.ALWAYS_TRUE;
    }

    protected Predicate<EzyObject> toAndPredicate(EzyArray query) {
        return EzyPredicates.and(toPredicateList(query));
    }

    protected Predicate<EzyObject> toOrPredicate(EzyArray query) {
        return EzyPredicates.or(toPredicateList(query));
    }

    protected List<Predicate> toPredicateList(EzyArray query) {
        List<Predicate> predicates = new ArrayList<>(query.size());
        for (int i = 0; i < query.size(); ++i) {
            Object q = query.get(i);
            if (q instanceof EzyObject) {
                predicates.add(toPredicate((EzyObject) q));
            } else {
                throw new InvalidQueryException("invalid query at: " + q);
            }
        }
        return predicates;
    }

    protected Predicate<EzyObject> toDefaultPredicate(EzyObject query) {
        for (Entry kv : query.entrySet()) {
            String field = null;
            Object value = kv.getValue();
            String fieldOrOperation = (String) kv.getKey();
            Operation operation = Operation.valueOfKeyword(fieldOrOperation);
            if (operation != null) {
                EzyObject objectValue = (EzyObject) value;
                for (Entry e : objectValue.entrySet()) {
                    field = (String) e.getKey();
                    value = e.getValue();
                    break;
                }
            } else if (value instanceof EzyObject) {
                EzyObject objectValue = (EzyObject) value;
                for (Entry e : objectValue.entrySet()) {
                    String keyword = (String) e.getKey();
                    operation = Operation.valueOfKeyword(keyword);
                    value = e.getValue();
                    break;
                }
            } else {
                field = fieldOrOperation;
                operation = Operation.EQ;
            }
            String queryField = field;
            Object queryValue = value;
            Operation queryOperation = operation;
            return record -> {
                Object recordValue = record.get(queryField);
                int compareResult = compareValue(recordValue, queryValue);
                if (queryOperation == Operation.GT) {
                    return compareResult > 0;
                }
                if (queryOperation == Operation.GTE) {
                    return compareResult >= 0;
                }
                if (queryOperation == Operation.LT) {
                    return compareResult < 0;
                }
                if (queryOperation == Operation.LTE) {
                    return compareResult <= 0;
                }
                if (queryOperation == Operation.NEQ) {
                    return compareResult != 0;
                }
                return compareResult == 0;
            };
        }
        return record -> false;
    }

    protected int compareValue(Object a, Object b) {
        if (a == null) {
            if (b == null) {
                return 0;
            }
            return -1;
        } else {
            if (b == null) {
                return 1;
            }
            Comparators comparators = Comparators.getInstance();
            Comparator comparator = comparators.getComparator(a.getClass());
            return comparator != null
                ? comparator.compare(a, b)
                : ((Comparable) a).compareTo(b);
        }
    }

    protected Map<String, Function<Object, Predicate<EzyObject>>> defaultBuilders() {
        Map<String, Function<Object, Predicate<EzyObject>>> map = new HashMap<>();
        map.put(Keywords.AND, q -> toAndPredicate((EzyArray) q));
        map.put(Keywords.OR, q -> toOrPredicate((EzyArray) q));
        return map;
    }
}
