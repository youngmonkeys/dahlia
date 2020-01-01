package com.tvd12.dahlia.core.setting;

public class FieldFloatSizeReader extends FieldSizeReader<FieldFloatSetting> {
	
	private static final FieldFloatSizeReader INSTANCE = new FieldFloatSizeReader();
	
	private FieldFloatSizeReader() {}
	
	public static FieldFloatSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldFloatSetting setting) {
		return Float.BYTES;
	}
	
}