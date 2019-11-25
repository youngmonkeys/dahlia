package com.tvd12.dahlia.core.codec;

import java.io.IOException;
import java.util.Map;

import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public interface FieldReaders {

	void read(FileProxy file, 
			Map<String, FieldSetting> settings, EzyObject output) throws IOException;

}
