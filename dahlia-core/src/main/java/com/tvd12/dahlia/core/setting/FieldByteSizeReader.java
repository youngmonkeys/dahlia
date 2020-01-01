package com.tvd12.dahlia.core.setting;

public class FieldByteSizeReader extends FieldSizeReader<FieldByteSetting> {
	
	private static final FieldByteSizeReader INSTANCE = new FieldByteSizeReader();
	
	private FieldByteSizeReader() {}
	
	public static FieldByteSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldByteSetting setting) {
		return Byte.BYTES;
	}
	
}