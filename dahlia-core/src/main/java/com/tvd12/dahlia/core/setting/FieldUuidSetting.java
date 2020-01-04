package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldUuidSetting extends FieldSetting {

	@Override
	public DataType getType() {
		return DataType.UUID;
	}
	
}
