package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;

@Getter
public class FieldArraySetting extends FieldEntitySetting {

	protected int maxSize;
	protected FieldSetting item;
	
	@Override
	public DataType getType() {
		return DataType.ARRAY;
	}
	
}
