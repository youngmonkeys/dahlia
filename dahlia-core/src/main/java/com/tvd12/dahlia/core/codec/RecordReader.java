package com.tvd12.dahlia.core.codec;

import java.io.IOException;
import java.util.Map;

import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class RecordReader {

	protected final FileProxy file;
	protected final FieldReaders fieldReaders;
	
	public RecordReader(FileProxy file) {
		this.file = file;
		this.fieldReaders = new FieldSimpleReaders();
	}
	
	public EzyObject read(
			Record record, 
			Map<String, FieldSetting> settings) {
		try {
			file.seek(record.getPosition());
			file.readyByte(); // header
			EzyObject output = EzyEntityFactory.newObject();
			fieldReaders.read(file, settings, output);
			return output;
		}
		catch(IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
