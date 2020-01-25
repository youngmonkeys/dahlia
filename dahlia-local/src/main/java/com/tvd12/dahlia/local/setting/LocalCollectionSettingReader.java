package com.tvd12.dahlia.local.setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldArraySetting;
import com.tvd12.dahlia.core.setting.FieldBigDecimalSetting;
import com.tvd12.dahlia.core.setting.FieldBooleanSetting;
import com.tvd12.dahlia.core.setting.FieldByteSetting;
import com.tvd12.dahlia.core.setting.FieldDoubleSetting;
import com.tvd12.dahlia.core.setting.FieldFloatSetting;
import com.tvd12.dahlia.core.setting.FieldIntegerSetting;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.dahlia.core.setting.FieldObjectSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.setting.FieldShortSetting;
import com.tvd12.dahlia.core.setting.FieldTextSetting;
import com.tvd12.dahlia.core.setting.FieldUuidSetting;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;
import com.tvd12.ezyfox.stream.EzyInputStreamLoader;
import com.tvd12.ezyfox.stream.EzyInputStreamReader;
import com.tvd12.ezyfox.stream.EzySimpleInputStreamReader;

public class LocalCollectionSettingReader {
	
	protected final EzyInputStreamLoader inputStreamLoader;
	protected final EzyInputStreamReader inputStreamReader;
	
	public LocalCollectionSettingReader() {
		this.inputStreamLoader = EzyAnywayInputStreamLoader.builder()
				.build();
		this.inputStreamReader = EzySimpleInputStreamReader.builder()
				.build();
	}
	
	public CollectionSetting readFileSetting(File file) {
		try {
			InputStream inputStream = new FileInputStream(file);
			return readInputStreamSetting(inputStream);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public CollectionSetting readFileSetting(String filePath) {
		InputStream inputStream = inputStreamLoader.load(filePath);
		return readInputStreamSetting(inputStream);
	}
	
	public CollectionSetting readInputStreamSetting(InputStream inputStream) {
		String json = inputStreamReader.readString(inputStream, EzyStrings.UTF_8);
		return readJsonSetting(json);
	}
	
	public CollectionSetting readJsonSetting(String json) {
		JSONObject object = new JSONObject(json);
		CollectionSetting setting = new CollectionSetting();
		String collectionName = object.getString(SettingFields.NAME);
		setting.setCollectionName(collectionName);
		JSONObject fieldSettings = object.getJSONObject(SettingFields.FIELDS);
		Map<String, FieldSetting> fields = readFieldSettings(fieldSettings);
		setting.setFields(fields);
		System.out.println("settings: " + setting.toMap());
		return setting;
	}
	
	protected Map<String, FieldSetting> readFieldSettings(JSONObject fieldSettings) {
		Map<String, FieldSetting> answer = new HashMap<>();
		for(String fieldName : fieldSettings.keySet()) {
			JSONObject fieldSetting = fieldSettings.getJSONObject(fieldName);
			DataType type = null;
			if(fieldSetting.has(SettingFields.TYPE)) 
				type = DataType.valueOfName(fieldSetting.getString(SettingFields.TYPE));
			else
				throw new IllegalArgumentException("'type' is required at field: " + fieldName);
			answer.put(fieldName, readFieldSetting(type, fieldSetting));
		}
		return answer;
	}
	
	protected FieldSetting readFieldSetting(DataType type, JSONObject setting) {
		FieldSetting field = null;
		if(type == DataType.BOOLEAN)
			field = readFieldBooleanSetting(setting);
		else if(type == DataType.BYTE)
			field = readFieldByteSetting(setting);
		else if(type == DataType.DOUBLE)
			field = readFieldDoubleSetting(setting);
		else if(type == DataType.FLOAT)
			field = readFieldFloatSetting(setting);
		else if(type == DataType.INTEGER)
			field = readFieldIntegerSetting(setting);
		else if(type == DataType.LONG)
			field = readFieldLongSetting(setting);
		else if(type == DataType.SHORT)
			field = readFieldShortSetting(setting);
		else if(type == DataType.TEXT)
			field = readFieldTextSetting(setting);
		else if(type == DataType.OBJECT)
			field = readFieldObjectSetting(setting);
		else if(type == DataType.ARRAY)
			field = readFieldArraySetting(setting);
		else if(type == DataType.UUID)
			field = readFieldUuidSetting(setting);
		else 
			field = readFieldBigDecimalSetting(setting);
		if(setting.has(SettingFields.NULLABLE))
			field.setNullable(setting.getBoolean(SettingFields.NULLABLE));
		return field;
	}
	
	protected FieldSetting readFieldBooleanSetting(JSONObject setting) {
		FieldBooleanSetting field = new FieldBooleanSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue(setting.getBoolean(SettingFields.DEFAULT));
		return field;
	}
	
	protected FieldSetting readFieldByteSetting(JSONObject setting) {
		FieldByteSetting field = new FieldByteSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue((byte)setting.getInt(SettingFields.DEFAULT));
		return field;
	}
	
	protected FieldSetting readFieldDoubleSetting(JSONObject setting) {
		FieldDoubleSetting field = new FieldDoubleSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue(setting.getDouble(SettingFields.DEFAULT));
		return field;
	}
	
	protected FieldSetting readFieldFloatSetting(JSONObject setting) {
		FieldFloatSetting field = new FieldFloatSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue(setting.getFloat(SettingFields.DEFAULT));
		return field;
	}
	
	protected FieldSetting readFieldIntegerSetting(JSONObject setting) {
		FieldIntegerSetting field = new FieldIntegerSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue(setting.getInt(SettingFields.DEFAULT));
		if(setting.has(SettingFields.MAX_VALUE))
			field.setMaxValue(setting.getInt(SettingFields.MAX_VALUE));
		return field;
	}
	
	protected FieldSetting readFieldLongSetting(JSONObject setting) {
		FieldLongSetting field = new FieldLongSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue(setting.getLong(SettingFields.DEFAULT));
		return field;
	}
	
	protected FieldSetting readFieldShortSetting(JSONObject setting) {
		FieldShortSetting field = new FieldShortSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue((short)setting.getInt(SettingFields.DEFAULT));
		return field;
	}
	
	protected FieldSetting readFieldTextSetting(JSONObject setting) {
		FieldTextSetting field = new FieldTextSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue(setting.getString(SettingFields.DEFAULT));
		if(setting.has(SettingFields.MAX_SIZE))
			field.setMaxSize(setting.getInt(SettingFields.MAX_VALUE));
		return field;
	}
	
	protected FieldSetting readFieldObjectSetting(JSONObject setting) {
		FieldObjectSetting field = new FieldObjectSetting();
		if(setting.has(SettingFields.FIELDS)) {
			JSONObject fieldSettings = setting.getJSONObject(SettingFields.FIELDS);
			Map<String, FieldSetting> fields = readFieldSettings(fieldSettings);
			field.setFields(fields);
		}
		else {
			throw new IllegalArgumentException("'fields' is required");
		}
		return field;
	}
	
	protected FieldSetting readFieldArraySetting(JSONObject setting) {
		FieldArraySetting field = new FieldArraySetting();
		if(setting.has(SettingFields.MAX_SIZE))
			field.setMaxSize(setting.getInt(SettingFields.MAX_SIZE));
		if(setting.has(SettingFields.ITEM)) {
			JSONObject itemSetting = setting.getJSONObject(SettingFields.ITEM);
			DataType itemType = null;
			if(itemSetting.has(SettingFields.TYPE))
				itemType = DataType.valueOfName(itemSetting.getString(SettingFields.TYPE));
			else
				throw new IllegalArgumentException("item 'type' is required");
			FieldSetting item = readFieldSetting(itemType, itemSetting);
			field.setItem(item);
		}
		else {
			throw new IllegalArgumentException("'item' field is required");
		}
		return field;
	}
	
	protected FieldSetting readFieldUuidSetting(JSONObject setting) {
		FieldUuidSetting field = new FieldUuidSetting();
		return field;
	}
	
	protected FieldSetting readFieldBigDecimalSetting(JSONObject setting) {
		FieldBigDecimalSetting field = new FieldBigDecimalSetting();
		if(setting.has(SettingFields.DEFAULT))
			field.setDefaultValue(setting.getBigDecimal(SettingFields.DEFAULT));
		return field;
	}

}
