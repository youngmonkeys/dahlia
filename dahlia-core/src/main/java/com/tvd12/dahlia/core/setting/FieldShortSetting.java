package com.tvd12.dahlia.core.setting;

import java.util.Map;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldShortSetting extends FieldSetting {

	protected Short defaultValue;
	
	@Override
	public DataType getType() {
		return DataType.SHORT;
	}
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = super.toMap();
		map.put(SettingFields.DEFAULT, defaultValue);
		return map;
	}
	
}
