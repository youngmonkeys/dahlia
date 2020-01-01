package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldArraySetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyArray;

public final class FieldArrayWriter extends FieldAbstractWriter<EzyArray> {

	private static final FieldArrayWriter INSTANCE = new FieldArrayWriter();
	
	private FieldArrayWriter() {}
	
	public static FieldArrayWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FieldWriters writers, 
			FileProxy file, 
			FieldSetting setting, EzyArray value) throws IOException {
		int size = value.size();
		file.writeShort((short)size);
		FieldArraySetting fs = (FieldArraySetting)setting;
		FieldSetting itemSetting = fs.getItem();
		for(int i = 0 ; i < size ; ++i)
			writers.write(file, itemSetting, value.get(i));
	}
		
}
