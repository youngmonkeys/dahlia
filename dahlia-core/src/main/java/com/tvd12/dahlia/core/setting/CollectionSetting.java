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

	protected int recordSize;
	protected int collectionId;
	protected String collectionName;
	protected Map<String, FieldSetting> fields; 
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put(SETTING_FIELD_ID, collectionId);
		map.put(SETTING_FIELD_NAME, collectionName);
		Map<String, Map<Object, Object>> fieldsToMaps = new HashMap<>();
		for(FieldSetting field : fields.values())
			fieldsToMaps.put(field.getName(), field.toMap());
		map.put(SETTING_FIELD_FIELDS, fieldsToMaps);
		return map;
	}
}
