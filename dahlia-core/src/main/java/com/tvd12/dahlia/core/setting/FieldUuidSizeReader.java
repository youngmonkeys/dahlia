package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.constant.Constants;

public class FieldUuidSizeReader extends FieldSizeReader<FieldUuidSetting> {
	
	private static final FieldUuidSizeReader INSTANCE = new FieldUuidSizeReader();
	
	private FieldUuidSizeReader() {}
	
	public static FieldUuidSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldUuidSetting setting) {
		return Constants.MAX_UUID_SIZE;
	}
	
}