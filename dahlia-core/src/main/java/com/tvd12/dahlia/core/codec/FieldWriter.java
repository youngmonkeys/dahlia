package com.tvd12.dahlia.core.codec;

import java.io.IOException;

import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;

public interface FieldWriter<T> {
	
	void write(
			FieldWriters writers,
			FileProxy file, 
			FieldSetting setting, T value) throws IOException;
	
}
