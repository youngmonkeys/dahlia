package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

interface FieldReader<T> {

	T read(FieldReaders readers,
			FileProxy file, 
			FieldSetting setting) throws IOException;
	
}
