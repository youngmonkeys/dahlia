package com.tvd12.dahlia.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ref<T> {

	protected T value;
	
	public boolean hasValue() {
		return value != null;
	}
	
	public boolean hasNoValue() {
		return value == null;
	}
	
}
