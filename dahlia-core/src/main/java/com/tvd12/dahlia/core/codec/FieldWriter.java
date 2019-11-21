package com.tvd12.dahlia.core.codec;

import java.io.IOException;

import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;

public interface FieldWriter<T> {
	
	default void write(
			FieldWriters serializers,
			FileProxy file, 
			FieldSetting setting, T value) throws IOException {
		write(file, setting, value);
	}
	
	default void write(
			FileProxy file, 
			FieldSetting setting, T value) throws IOException {
	}
	
}
