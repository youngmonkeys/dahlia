package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldTextSetting extends FieldSetting {

	protected String defaultValue;
	protected int maxSize;
	
	@Override
	public DataType getType() {
		return DataType.TEXT;
	}

}
