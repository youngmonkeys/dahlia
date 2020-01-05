package com.tvd12.dahlia.query;

import lombok.Getter;

@Getter
public class UpdateOptions {

	protected boolean multi;
	
	public UpdateOptions setMulti(boolean multi) {
		this.multi = multi;
		return this;
	}
	
}
