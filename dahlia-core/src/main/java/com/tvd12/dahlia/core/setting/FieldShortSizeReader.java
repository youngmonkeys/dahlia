package com.tvd12.dahlia.core.setting;

public class FieldShortSizeReader extends FieldSizeReader<FieldShortSetting> {
	
	private static final FieldShortSizeReader INSTANCE = new FieldShortSizeReader();
	
	private FieldShortSizeReader() {}
	
	public static FieldShortSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldShortSetting setting) {
		return Short.BYTES;
	}
	
}