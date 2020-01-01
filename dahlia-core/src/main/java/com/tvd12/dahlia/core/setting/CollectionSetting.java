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
	protected FieldSetting id;
	protected Map<String, FieldSetting> fields; 
	
	public CollectionSetting() {
		this.fields = new HashMap<>();
	}
	
	public void setFields(Map<String, FieldSetting> fields) {
		this.fields.putAll(fields);
		this.id = this.fields.remove(FIELD_ID);
	}
	
	public Map<String, FieldSetting> getAllFields() {
		Map<String, FieldSetting> all = new HashMap<>(fields);
		all.put(FIELD_ID, id);
		return all;
	}
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put(SETTING_FIELD_ID, collectionId);
		map.put(SETTING_FIELD_NAME, collectionName);
		Map<String, Map<Object, Object>> fieldsToMaps = new HashMap<>();
		fieldsToMaps.put(FIELD_ID, id.toMap());
		for(String fieldName : fields.keySet()) {
			FieldSetting field = fields.get(fieldName);
			fieldsToMaps.put(fieldName, field.toMap());
		}
		map.put(SETTING_FIELD_FIELDS, fieldsToMaps);
		return map;
	}
}
