package com.tvd12.dahlia.core.setting;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldLongSetting extends FieldSetting {

	protected Long defaultValue;
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = super.toMap();
		map.put("defaultValue", defaultValue);
		return map;
	}
	
}
