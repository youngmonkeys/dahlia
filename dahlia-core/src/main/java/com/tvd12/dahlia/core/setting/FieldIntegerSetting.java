package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldIntegerSetting extends FieldSetting {

	protected Integer defaultValue;
	
	@Override
	public DataType getType() {
		return DataType.INTEGER;
	}
	
}
