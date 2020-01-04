package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.math.BigDecimal;

import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldBigDecimalWriter extends FieldAbstractWriter<BigDecimal> {

	private static final FieldBigDecimalWriter INSTANCE = new FieldBigDecimalWriter();
	
	private FieldBigDecimalWriter() {}
	
	public static FieldBigDecimalWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, BigDecimal value) throws IOException {
		String str = value.toString();
		int maxLength = Constants.MAX_BIGDECIMAL_SIZE;
		if(str.length() > maxLength)
			str = str.substring(0, maxLength);
		byte[] bytes = str.getBytes();
		file.writeByte((byte)bytes.length);
		file.writeBytes(bytes);
	}
	
}
