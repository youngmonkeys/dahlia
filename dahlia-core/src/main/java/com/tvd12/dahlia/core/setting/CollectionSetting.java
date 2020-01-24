package com.tvd12.dahlia.core.setting;

import static com.tvd12.dahlia.core.constant.Constants.FIELD_ID;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_FIELDS;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_ID;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_INDEXES;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tvd12.ezyfox.util.EzyToMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CollectionSetting implements EzyToMap {

	protected int recordSize;
	protected int collectionId;
	protected String collectionName;
	protected FieldSetting id;
	protected Map<String, FieldSetting> fields; 
	protected Map<String, IndexSetting> indexes;
	
	public CollectionSetting() {
		this.fields = new HashMap<>();
		this.indexes = new HashMap<>();
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
	
	public void setIndexes(List<IndexSetting> indexes) {
		for(IndexSetting index : indexes)
			this.indexes.put(index.getName(), index);
	}
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = new HashMap<>();
		map.put(SETTING_FIELD_ID, collectionId);
		map.put(SETTING_FIELD_NAME, collectionName);
		Map<String, Map<Object, Object>> fieldsToMap = new HashMap<>();
		fieldsToMap.put(FIELD_ID, id.toMap());
		for(String fieldName : fields.keySet()) {
			FieldSetting field = fields.get(fieldName);
			fieldsToMap.put(fieldName, field.toMap());
		}
		map.put(SETTING_FIELD_FIELDS, fieldsToMap);
		List<Map<Object, Object>> indexesToList = new ArrayList<>();
		for(IndexSetting index : indexes.values()) {
			indexesToList.add(index.toMap());
		}
		map.put(SETTING_FIELD_INDEXES, indexesToList);
		return map;
	}
}
