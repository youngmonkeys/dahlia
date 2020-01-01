package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_FIELDS;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_ID;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_RECORD_SIZE;

import java.util.Map;

import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingCollectionDeserializer 
		implements SettingDeserializer<CollectionSetting> {

	protected final SettingObjectToFields objectToFields;
	protected final EzyObjectDeserializer objectDeserializer;
	
	public SettingCollectionDeserializer(EzyObjectDeserializer objectDeserializer) {
		this.objectDeserializer = objectDeserializer;
		this.objectToFields = new SettingObjectToFields();
	}
	
	@Override
	public CollectionSetting deserialize(byte[] bytes) {
		EzyObject object = objectDeserializer.deserialize(bytes);
		CollectionSetting setting = new CollectionSetting();
		setting.setCollectionId(object.get(SETTING_FIELD_ID, int.class));
		setting.setRecordSize(object.get(SETTING_FIELD_RECORD_SIZE, int.class));
		EzyArray fieldArray = object.get(SETTING_FIELD_FIELDS);
		Map<String, FieldSetting> fields = arrayToFields(fieldArray);
		setting.setFields(fields);
		return setting;
		
	}
	
	public Map<String, FieldSetting> arrayToFields(EzyArray array) {
		Map<String, FieldSetting> fields = objectToFields.toFieldSettings(array);
		return fields;
	}
	
}
