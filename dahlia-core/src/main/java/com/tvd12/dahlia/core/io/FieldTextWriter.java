package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.io.EzyStrings;

final class FieldTextWriter extends FieldAbstractWriter<String> {

	private static final FieldTextWriter INSTANCE = new FieldTextWriter();
	
	private FieldTextWriter() {}
	
	public static FieldTextWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, String value) throws IOException {
		byte[] bytes = EzyStrings.getUtfBytes(value);
		file.writeInt(bytes.length);
		file.writeBytes(bytes);
	}
	
}
