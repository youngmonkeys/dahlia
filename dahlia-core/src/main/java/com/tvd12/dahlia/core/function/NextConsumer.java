package com.tvd12.dahlia.core.function;

import java.util.function.Consumer;

public interface NextConsumer<T> extends Consumer<T> {

    default boolean next() {
        return true;
    }
}
