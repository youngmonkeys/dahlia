package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.*;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldBooleanSetting;
import com.tvd12.dahlia.core.setting.FieldIntegerSetting;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.setting.FieldTextSetting;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class SettingCollectionSerializer implements SettingSerializer<CollectionSetting> {

	protected final SettingFieldToObjects fieldToObjects;
	protected final EzyObjectSerializer objectSerializer;
	
	public SettingCollectionSerializer(EzyObjectSerializer objectSerializer) {
		this.objectSerializer = objectSerializer;
		this.fieldToObjects = new SettingFieldToObjects();
	}
	
	@Override
	public byte[] serialize(CollectionSetting setting) {
		EzyObject object = collectionToObject(setting);
		byte[] bytes = objectSerializer.serialize(object);
		return bytes;
	}

	protected EzyObject collectionToObject(CollectionSetting setting) {
		return EzyEntityFactory.newObjectBuilder()
				.append(SETTING_FIELD_ID, setting.getCollectionId())
				.append(SETTING_FIELD_RECORD_SIZE, setting.getRecordSize())
				.append(SETTING_FIELD_FIELDS, fieldsToArray(setting))
				.build();
	}

	protected EzyArray fieldsToArray(CollectionSetting setting) {
		Map<String, FieldSetting> fields = setting.getFields();
		EzyArrayBuilder builder = EzyEntityFactory.newArrayBuilder();
		builder.append(fieldToObject(setting.getId()));
		for(FieldSetting field : fields.values())
			builder.append(fieldToObject(field));
		return builder.build();
	}

	protected EzyObject fieldToObject(FieldSetting field) {
		return fieldToObjects.toObject(field);
	}
}

class SettingFieldToObjects {
	
	protected final Map<DataType, SettingFieldToObject> mappers;
	
	public SettingFieldToObjects() {
		this.mappers = defaultMappers();
	}
	
	public EzyObject toObject(FieldSetting setting) {
		SettingFieldToObject mapper = mappers.get(setting.getType());
		EzyObject answer = mapper.toObject(setting);
		return answer;
	}
	
	protected Map<DataType, SettingFieldToObject> defaultMappers() {
		Map<DataType, SettingFieldToObject> map = new HashMap<>();
		map.put(DataType.BOOLEAN, SettingFieldBooleanToObject.getInstance());
		map.put(DataType.INTEGER, SettingFieldIntegerToObject.getInstance());
		map.put(DataType.LONG, SettingFieldLongToObject.getInstance());
		map.put(DataType.TEXT, SettingFieldTextToObject.getInstance());
		return map;
	}
}

abstract class SettingFieldToObject<S extends FieldSetting> {

	public final EzyObject toObject(S setting) {
		return newObjectBuilder(setting).append(SETTING_FIELD_NAME, setting.getName())
		        .append(SETTING_FIELD_TYPE, setting.getType()).append(SETTING_FIELD_NULLABLE, setting.isNullable())
		        .build();
	}

	protected EzyObjectBuilder newObjectBuilder(S setting) {
		return EzyEntityFactory.newObjectBuilder();
	}

}

class SettingFieldBooleanToObject extends SettingFieldToObject<FieldBooleanSetting> {

	private static final SettingFieldBooleanToObject INSTANCE 
			= new SettingFieldBooleanToObject();

	private SettingFieldBooleanToObject() {}

	public static SettingFieldBooleanToObject getInstance() {
		return INSTANCE;
	}

	@Override
	protected EzyObjectBuilder newObjectBuilder(FieldBooleanSetting setting) {
		return super.newObjectBuilder(setting);
	}

}

class SettingFieldIntegerToObject extends SettingFieldToObject<FieldIntegerSetting> {

	private static final SettingFieldIntegerToObject INSTANCE 
			= new SettingFieldIntegerToObject();

	private SettingFieldIntegerToObject() {}

	public static SettingFieldIntegerToObject getInstance() {
		return INSTANCE;
	}

}

class SettingFieldLongToObject extends SettingFieldToObject<FieldLongSetting> {

	private static final SettingFieldLongToObject INSTANCE 
			= new SettingFieldLongToObject();

	private SettingFieldLongToObject() {}

	public static SettingFieldLongToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(FieldLongSetting setting) {
		return super.newObjectBuilder(setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}

class SettingFieldTextToObject extends SettingFieldToObject<FieldTextSetting> {

	private static final SettingFieldTextToObject INSTANCE 
			= new SettingFieldTextToObject();

	private SettingFieldTextToObject() {}

	public static SettingFieldTextToObject getInstance() {
		return INSTANCE;
	}

}
