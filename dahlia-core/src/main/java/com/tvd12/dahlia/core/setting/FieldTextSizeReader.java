package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.constant.Constants;

public class FieldTextSizeReader extends FieldSizeReader<FieldTextSetting> {
	
	private static final FieldTextSizeReader INSTANCE = new FieldTextSizeReader();
	
	private FieldTextSizeReader() {}
	
	public static FieldTextSizeReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected int readDataSize(FieldTextSetting setting) {
		int answer = 0;
		answer += Constants.MAX_STRING_SIZE_BYTES;
		answer += setting.getMaxSize() * Constants.MAX_UTF8_CHAR_BYTES;
		return answer;
	}
	
}