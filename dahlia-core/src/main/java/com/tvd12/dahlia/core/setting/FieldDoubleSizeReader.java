package com.tvd12.dahlia.core.setting;

final class FieldDoubleSizeReader extends FieldSizeReader<FieldDoubleSetting> {
	
	private static final FieldDoubleSizeReader INSTANCE = new FieldDoubleSizeReader();
	
	private FieldDoubleSizeReader() {}
	
	public static FieldDoubleSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldDoubleSetting setting) {
		return Double.BYTES;
	}
	
}