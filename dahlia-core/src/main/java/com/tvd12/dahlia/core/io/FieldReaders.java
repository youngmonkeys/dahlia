package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.Map;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public interface FieldReaders {
	
	String readName(
			FileProxy file) throws IOException;
	
	Object readValue(
			FileProxy file, 
			FieldSetting setting) throws IOException;
	
	Object read(
			FileProxy file, 
			FieldSetting setting) throws IOException;

	void read(FileProxy file, 
			Map<String, FieldSetting> settings, EzyObject output) throws IOException;

}
