package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldArraySetting extends FieldEntitySetting {

	protected int maxSize;
	protected FieldSetting item;
	
	public FieldArraySetting() {
		this.maxSize = Byte.MAX_VALUE;
	}
	
	@Override
	public DataType getType() {
		return DataType.ARRAY;
	}
	
}
