package com.tvd12.dahlia.core.setting;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.util.EzyToMap;

import lombok.Getter;
import lombok.Setter;
import static com.tvd12.dahlia.core.constant.Constants.*;

@Setter
@Getter
public class CollectionSetting implements EzyToMap {

	protected String name;
	protected Map<String, FieldSetting> mappings; 
	
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put(SETTING_FIELD_NAME, name);
		Map<String, Map<Object, Object>> fieldsToMaps = new HashMap<>();
		for(FieldSetting field : mappings.values())
			fieldsToMaps.put(field.getName(), field.toMap());
		map.put(SETTING_FIELD_MAPPINGS, fieldsToMaps);
		return map;
	}
}
