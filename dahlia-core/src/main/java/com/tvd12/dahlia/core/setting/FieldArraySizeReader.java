package com.tvd12.dahlia.core.setting;

public class FieldArraySizeReader extends FieldSizeReader<FieldArraySetting> {
	
	private static final FieldArraySizeReader INSTANCE = new FieldArraySizeReader();
	
	private FieldArraySizeReader() {}
	
	public static FieldArraySizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldArraySetting setting) {
		return Short.BYTES;
	}
	
	@Override
	protected int readPropertiesSize(FieldSizeReaders readers, FieldArraySetting setting) {
		int itemSize = readers.readFieldSize(setting.getItem());
		return itemSize * setting.getMaxSize();
	}
	
}