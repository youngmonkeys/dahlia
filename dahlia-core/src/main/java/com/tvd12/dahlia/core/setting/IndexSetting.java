package com.tvd12.dahlia.core.setting;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.util.EzyToMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IndexSetting implements EzyToMap {

	protected final String name;
	protected final Map<String, Boolean> fields;
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put("name", name);
		map.put("fields", fields);
		return map;
	}
	
	
}
