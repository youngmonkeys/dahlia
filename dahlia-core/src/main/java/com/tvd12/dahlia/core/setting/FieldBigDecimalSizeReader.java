package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.constant.Constants;

public class FieldBigDecimalSizeReader extends FieldSizeReader<FieldBigDecimalSetting> {
	
	private static final FieldBigDecimalSizeReader INSTANCE = new FieldBigDecimalSizeReader();
	
	private FieldBigDecimalSizeReader() {}
	
	public static FieldBigDecimalSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldBigDecimalSetting setting) {
		return Constants.MAX_BIGDECIMAL_SIZE;
	}
	
}