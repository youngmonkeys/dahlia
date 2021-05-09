package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.Map;

import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import static com.tvd12.dahlia.core.constant.Constants.*;

@SuppressWarnings("rawtypes")
public class RecordReader {

	protected final FileProxy file;
	protected final FieldReaders fieldReaders;
	
	public RecordReader(FileProxy file) {
		this.file = file;
		this.fieldReaders = new FieldSimpleReaders();
	}
	
	public boolean hasMoreRecords(long position) {
		try {
			long fileLength = file.length();
			if(position < fileLength)
				return true;
			return false;
		}
		catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public Record read(long position, FieldSetting idSetting) {
		return read(position, idSetting, true);
	}
	
	public Record read(
			long position, 
			FieldSetting idSetting, boolean ignoreDeleted) {
		try {
			file.seek(position);
			byte header = file.readByte();
			boolean deleted = (header & (1 << 0)) == 0;
			if(ignoreDeleted && deleted)
				return null;
			Object id = fieldReaders.read(file, idSetting);
			Record record = new Record((Comparable) id, position);
			record.setAlive(!deleted);
			return record;
		}
		catch(IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public EzyObject read(
			Record record, 
			FieldSetting idSetting,
			Map<String, FieldSetting> settings) {
		try {
			file.seek(record.getPosition());
			file.readByte(); // header
			EzyObject output = EzyEntityFactory.newObject();
			Object id = fieldReaders.read(file, idSetting);
			output.put(FIELD_ID, id);
			fieldReaders.read(file, settings, output);
			return output;
		}
		catch(IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
