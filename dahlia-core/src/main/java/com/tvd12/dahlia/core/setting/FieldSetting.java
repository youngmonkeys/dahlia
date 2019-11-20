package com.tvd12.dahlia.core.setting;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.ezyfox.util.EzyToMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldSetting implements EzyToMap {

	protected String name;
	protected DataType type;
	protected boolean nullable;
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("nullable", nullable);
		return map;
	}
}
