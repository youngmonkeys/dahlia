package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.constant.Constants;

public class FieldBigDecimalSizeReader extends FieldSizeReader<FieldTextSetting> {
	
	private static final FieldBigDecimalSizeReader INSTANCE = new FieldBigDecimalSizeReader();
	
	private FieldBigDecimalSizeReader() {}
	
	public static FieldBigDecimalSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldTextSetting setting) {
		return Constants.MAX_BIGDECIMAL_SIZE;
	}
	
}