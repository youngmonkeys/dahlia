package com.tvd12.dahlia.core.storage;

import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.io.FileRandomAccess;
import com.tvd12.dahlia.core.io.RecordReader;
import com.tvd12.dahlia.core.io.RecordWriter;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static com.tvd12.dahlia.core.constant.Constants.FILE_RECORDS_DATA;
import static com.tvd12.dahlia.core.constant.Constants.MODE_READ_WRITE;

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

    public boolean hasMoreRecords(long position) {
        return recordReader.hasMoreRecords(position);
    }

    public Record read(
        long position,
        FieldSetting idSetting
    ) {
        return recordReader.read(position, idSetting);
    }

    public EzyObject read(
        Record record,
        FieldSetting idSetting,
        Map<String, FieldSetting> settings
    ) {
        return recordReader.read(record, idSetting, settings);
    }

    public void write(
        Record record,
        FieldSetting idSetting,
        Map<String, FieldSetting> settings,
        EzyObject data
    ) {
        recordWriter.write(record, idSetting, settings, data);
    }
}