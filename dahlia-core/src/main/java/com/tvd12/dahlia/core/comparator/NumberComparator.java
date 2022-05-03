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
        int result;
        if (isFloatNumber(o1)) {
            if (isFloatNumber(o2)) {
                result = Double.compare(o1.doubleValue(), o2.doubleValue());
            } else {
                throw new IllegalArgumentException("can't compare a float number to int number");
            }
        } else {
            if (isIntNumber(o2)) {
                result = Long.compare(o1.longValue(), o2.longValue());
            } else {
                throw new IllegalArgumentException("can't compare a int number to float number");
            }
        }
        return result;
    }

    private boolean isIntNumber(Number number) {
        return number instanceof Byte
            || number instanceof Integer
            || number instanceof Long
            || number instanceof Short;
    }

    private boolean isFloatNumber(Number number) {
        return number instanceof Float
            || number instanceof Double;
    }
}
