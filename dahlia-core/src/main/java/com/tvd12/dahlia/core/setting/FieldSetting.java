package com.tvd12.dahlia.core.setting;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.ezyfox.util.EzyToMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class FieldSetting implements EzyToMap {

	protected boolean nullable;
	
	public abstract DataType getType();
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put("type", getType());
		map.put("nullable", nullable);
		return map;
	}
	
}
