package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldTextSetting extends FieldSetting {

	protected int maxSize;
	protected String defaultValue;
	
	public FieldTextSetting() {
		this.maxSize = 255;
	}
	
	@Override
	public DataType getType() {
		return DataType.TEXT;
	}

}
