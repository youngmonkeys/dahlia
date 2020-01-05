package com.tvd12.dahlia.query;

import lombok.Getter;

@Getter
public class DeleteOptions {

	protected boolean multi;
	
	public DeleteOptions setMulti(boolean multi) {
		this.multi = multi;
		return this;
	}
	
}
