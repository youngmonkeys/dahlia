package com.tvd12.dahlia.core.codec;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import static com.tvd12.dahlia.core.constant.Constants.*;

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
		Map<String, FieldSetting> fields = new HashMap<>();
		for(int i = 0 ; i < array.size() ; ++i) {
			EzyObject object = array.get(i);
			FieldSetting field = objectToField(object);
			fields.put(field.getName(), field);
		}
		return fields;
	}
	
	public FieldSetting objectToField(EzyObject object) {
		FieldSetting setting = objectToFields.toSetting(object);
		return setting;
	}
	
}

class SettingObjectToFields {
	
	protected final Map<DataType, SettingObjectToField> mappers;
	
	public SettingObjectToFields() {
		this.mappers = defaultMappers();
	}
	
	public FieldSetting toSetting(EzyObject object) {
		DataType type = DataType.valueOf(object.get(SETTING_FIELD_TYPE));
		SettingObjectToField mapper = mappers.get(type);
		FieldSetting answer = mapper.toSetting(object);
		answer.setType(type);
		return answer;
	}
	
	protected Map<DataType, SettingObjectToField> defaultMappers() {
		Map<DataType, SettingObjectToField> map = new HashMap<>();
//		map.put(DataType.BOOLEAN, SettingFieldBooleanToObject.getInstance());
//		map.put(DataType.INTEGER, SettingFieldIntegerToObject.getInstance());
		map.put(DataType.LONG, SettingObjectToLongField.getInstance());
//		map.put(DataType.TEXT, SettingFieldTextToObject.getInstance());
		return map;
	}
}

abstract class SettingObjectToField<S extends FieldSetting> {
	
	public final S toSetting(EzyObject object) {
		S setting = newSetting(object);
		setting.setName(object.get(SETTING_FIELD_NAME));
		setting.setNullable(object.get(SETTING_FIELD_NULLABLE));
		return setting;
	}
	
	protected abstract S newSetting(EzyObject object);
	
}

class SettingObjectToLongField extends SettingObjectToField<FieldLongSetting> {

	private static final SettingObjectToLongField INSTANCE = new SettingObjectToLongField();
	
	private SettingObjectToLongField() {}
	
	public static SettingObjectToLongField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldLongSetting newSetting(EzyObject object) {
		FieldLongSetting setting = new FieldLongSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, long.class));
		return setting;
	}
	
}
