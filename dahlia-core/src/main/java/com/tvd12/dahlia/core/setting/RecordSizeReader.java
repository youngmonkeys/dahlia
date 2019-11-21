package com.tvd12.dahlia.core.setting;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.data.DataType;

public class RecordSizeReader {

	private final FieldSizeReaders fieldSizeReaders;
	
	public RecordSizeReader() {
		this.fieldSizeReaders = new FieldSizeReaders();
	}
	
	public int read(Map<String, FieldSetting> settings) {
		return read(settings.values());
	}
	
	public int read(Collection<FieldSetting> settings) {
		int size = 0;
		size += Constants.RECORD_HEADER_SIZE;
		size += fieldSizeReaders.read(settings);
		return size;
	}
	
}

class FieldSizeReaders {
	
	protected final Map<DataType, FieldSizeReader> readers;
	
	public FieldSizeReaders() {
		this.readers = defaultReaders();
	}
	
	public int read(FieldSetting setting) {
		FieldSizeReader reader = readers.get(setting.getType());
		int size = reader.read(setting);
		return size;
	}
	
	public int read(Collection<FieldSetting> settings) {
		int size = 0;
		for(FieldSetting setting : settings)
			size += read(setting);
		return size;
	}
	
	public int read(Map<String, FieldSetting> settings) {
		return read(settings.values());
	}
	
	protected Map<DataType, FieldSizeReader> defaultReaders() {
		Map<DataType, FieldSizeReader> map = new HashMap<>();
		map.put(DataType.LONG, FieldLongSizeReader.getInstance());
		return map;
	}
	
}

abstract class FieldSizeReader<S extends FieldSetting> {
	
	public final int read(S setting) {
		int size = 0;
		size += readHeaderSize(setting);
		size += readFieldNameSize(setting.getName());
		size += readDataSize();
		size += readPropertiesSize();
		return size;
		
	}
	
	protected final int readHeaderSize(S setting) {
		if(setting.isNullable())
			return 1;
		return 0;
	}
	
	protected final int readFieldNameSize(String name) {
		return name.length();
	}
	
	protected abstract int readDataSize();
	protected abstract int readPropertiesSize();
	
}

class FieldLongSizeReader extends FieldSizeReader<FieldLongSetting> {
	
	private static final FieldLongSizeReader INSTANCE = new FieldLongSizeReader();
	
	private FieldLongSizeReader() {}
	
	public static FieldLongSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize() {
		return Long.BYTES;
	}
	
	@Override
	protected int readPropertiesSize() {
		int defaultValueSize = Long.BYTES;
		int size = 0;
		size += defaultValueSize;
		return size;
	}
	
}