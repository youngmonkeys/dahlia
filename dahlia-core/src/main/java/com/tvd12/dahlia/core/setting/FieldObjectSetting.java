package com.tvd12.dahlia.core.setting;

import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldObjectSetting extends FieldEntitySetting {
	
	protected Map<String, FieldSetting> fields;
	
	@Override
	public DataType getType() {
		return DataType.OBJECT;
	}

}
