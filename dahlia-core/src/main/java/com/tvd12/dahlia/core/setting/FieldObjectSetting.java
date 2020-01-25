package com.tvd12.dahlia.core.setting;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.constant.SettingFields;
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

	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = super.toMap();
		Map<String, Map<Object, Object>> fieldsToMap = new HashMap<>();
		for(String fieldName : fields.keySet()) {
			FieldSetting field = fields.get(fieldName);
			fieldsToMap.put(fieldName, field.toMap());
		}
		map.put(SettingFields.FIELDS, fieldsToMap);
		return map;
		
	}
}
