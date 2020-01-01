package com.tvd12.dahlia.core.setting;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tvd12.dahlia.core.data.DataType;

public class FieldSizeReaders {
	
	protected final Map<DataType, FieldSizeReader> readers;
	
	public FieldSizeReaders() {
		this.readers = defaultReaders();
	}
	
	public int read(String fieldName, FieldSetting setting) {
		int size = 0;
		size += readFieldNameSize(fieldName);
		size += readFieldSize(setting);
		return size;
	}
	
	public int readFieldSize(FieldSetting setting) {
		FieldSizeReader reader = readers.get(setting.getType());
		int size = 0;
		size += readFieldHeaderSize(setting);
		size += reader.read(this, setting);
		return size;
	}
	
	public int read(Map<String, FieldSetting> settings) {
		int size = 0;
		for(Entry<String, FieldSetting> entry : settings.entrySet()) {
			int fieldSize = read(entry.getKey(), entry.getValue());
			size += fieldSize;
		}
		return size;
	}
	
	protected final int readFieldHeaderSize(FieldSetting setting) {
		if(setting.isNullable())
			return 1;
		return 0;
	}
	
	protected final int readFieldNameSize(String fieldName) {
		int length = fieldName.length();
		return length;
	}
	
	protected Map<DataType, FieldSizeReader> defaultReaders() {
		Map<DataType, FieldSizeReader> map = new HashMap<>();
		map.put(DataType.BOOLEAN, FieldBooleanSizeReader.getInstance());
		map.put(DataType.BYTE, FieldByteSizeReader.getInstance());
		map.put(DataType.DOUBLE, FieldDoubleSizeReader.getInstance());
		map.put(DataType.FLOAT, FieldFloatSizeReader.getInstance());
		map.put(DataType.INTEGER, FieldIntegerSizeReader.getInstance());
		map.put(DataType.LONG, FieldLongSizeReader.getInstance());
		map.put(DataType.SHORT, FieldShortSizeReader.getInstance());
		map.put(DataType.TEXT, FieldTextSizeReader.getInstance());
		map.put(DataType.ARRAY, FieldArraySizeReader.getInstance());
		map.put(DataType.OBJECT, FieldObjectSizeReader.getInstance());
		return map;
	}
	
}