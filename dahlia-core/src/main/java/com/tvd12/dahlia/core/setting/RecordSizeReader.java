package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.core.constant.Constants;

import java.util.Map;

public class RecordSizeReader {

    private final FieldSizeReaders fieldSizeReaders;

    public RecordSizeReader() {
        this.fieldSizeReaders = new FieldSizeReaders();
    }

    public int read(Map<String, FieldSetting> settings) {
        int size = 0;
        size += Constants.RECORD_HEADER_SIZE;
        size += fieldSizeReaders.read(settings);
        return size;
    }
}