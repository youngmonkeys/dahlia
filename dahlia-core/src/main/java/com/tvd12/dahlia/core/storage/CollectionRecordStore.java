package com.tvd12.dahlia.core.storage;

import static com.tvd12.dahlia.core.constant.Constants.FILE_RECORDS_DATA;
import static com.tvd12.dahlia.core.constant.Constants.MODE_READ_WRITE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.tvd12.dahlia.core.codec.RecordReader;
import com.tvd12.dahlia.core.codec.RecordWriter;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.io.FileRandomAccess;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

class CollectionRecordStore {
	
	protected final FileProxy file;
	protected final Path filePath;
	protected final RecordWriter recordWriter;
	protected final RecordReader recordReader;
	
	public CollectionRecordStore(Path collectionDirectoryPath) {
		this.filePath = Paths.get(collectionDirectoryPath.toString(), FILE_RECORDS_DATA);
		this.file = new FileRandomAccess(filePath.toFile(), MODE_READ_WRITE);
		this.recordReader = new RecordReader(file);
		this.recordWriter = new RecordWriter(file);
	}
	
	public EzyObject read(
			Record record, 
			Map<String, FieldSetting> settings) {
		EzyObject value = recordReader.read(record, settings);
		return value;
	}
	
	public void write(
			Record record, 
			Map<String, FieldSetting> settings, EzyObject data) {
		recordWriter.write(record, settings, data);
	}
	
}