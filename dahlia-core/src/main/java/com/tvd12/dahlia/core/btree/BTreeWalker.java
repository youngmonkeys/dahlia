package com.tvd12.dahlia.core.btree;

public interface BTreeWalker {

	void accept(Integer entry);
	
	default boolean next() {
		return true;
	}
	
}
