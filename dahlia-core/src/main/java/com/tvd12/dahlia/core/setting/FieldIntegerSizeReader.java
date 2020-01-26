package com.tvd12.dahlia.core.setting;

final class FieldIntegerSizeReader extends FieldSizeReader<FieldIntegerSetting> {
	
	private static final FieldIntegerSizeReader INSTANCE = new FieldIntegerSizeReader();
	
	private FieldIntegerSizeReader() {}
	
	public static FieldIntegerSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldIntegerSetting setting) {
		return Integer.BYTES;
	}
	
}