package com.tvd12.dahlia.local;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldArraySetting;
import com.tvd12.dahlia.core.setting.FieldIntegerSetting;
import com.tvd12.dahlia.core.setting.FieldObjectSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.setting.FieldTextSetting;
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
	
	public CollectionSetting readFileSetting(String filePath) {
		InputStream inputStream = inputStreamLoader.load(filePath);
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
				throw new IllegalArgumentException("'type' is required");
			answer.put(fieldName, readFieldSetting(type, fieldSetting));
		}
		return answer;
	}
	
	protected FieldSetting readFieldSetting(DataType type, JSONObject setting) {
		FieldSetting field = null;
		if(type == DataType.INTEGER)
			field = readFieldIntegerSetting(setting);
		if(type == DataType.TEXT)
			field = readFieldTextSetting(setting);
		else if(type == DataType.OBJECT)
			field = readFieldObjectSetting(setting);
		else if(type == DataType.ARRAY)
			field = readFieldArraySetting(setting);
		if(setting.has(SettingFields.NULLABLE))
			field.setNullable(setting.getBoolean(SettingFields.NULLABLE));
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
}
