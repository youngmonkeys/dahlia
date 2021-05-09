package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.Map;

import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;
import static com.tvd12.dahlia.core.constant.Constants.*;

@SuppressWarnings("rawtypes")
public class RecordWriter {

	protected final FileProxy file;
	protected final FieldWriters fieldWriters;
	
	public RecordWriter(FileProxy file) {
		this.file = file;
		this.fieldWriters = new FieldSimpleWriters();
	}
	
	public void write(
			Record record,
			FieldSetting idSetting,
			Map<String, FieldSetting> settings, EzyObject data) {
		try {
			file.seek(record.getPosition());
			writeRecordHeader(record);
			if(record.isAlive()) {
				Comparable id = data.get(FIELD_ID);
				fieldWriters.write(file, FIELD_ID, idSetting, id);
				fieldWriters.write(file, settings, data);
			}
		}
		catch(IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	protected void writeRecordHeader(Record record) throws IOException {
		byte header = 0;
		if(record.isAlive())
			header |= 1 << 0;
		file.writeByte(header);
	}
	
}
