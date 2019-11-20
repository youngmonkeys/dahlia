package com.tvd12.dahlia.core.entity;

import lombok.Getter;

@Getter
public class Record {

	protected final Comparable id;
	protected final int[] location;
	
	public Record(Comparable id, int[] location) {
		this.id = id;
		this.location = location;
	}
	
}
