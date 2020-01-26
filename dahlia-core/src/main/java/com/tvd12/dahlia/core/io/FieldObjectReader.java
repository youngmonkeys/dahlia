package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.Map;

import com.tvd12.dahlia.core.setting.FieldObjectSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

final class FieldObjectReader extends FieldAbstractReader<EzyObject> {

	private static final FieldObjectReader INSTANCE = new FieldObjectReader();
	
	private FieldObjectReader() {}
	
	public static FieldObjectReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObject readValue(
			FieldReaders readers, 
			FileProxy file, FieldSetting setting) throws IOException {
		FieldObjectSetting fs = (FieldObjectSetting)setting;
		Map<String, FieldSetting> fieldSettings = fs.getFields();
		EzyObject object = EzyEntityFactory.newObject();
		readers.read(file, fieldSettings, object);
		return object;
	}
	
}
