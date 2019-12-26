package com.tvd12.dahlia.core.function;

import java.util.function.Consumer;

import com.tvd12.dahlia.core.entity.Record;

public interface RecordConsumer extends Consumer<Record> {

	default boolean next() {
		return true;
	}
	
}
