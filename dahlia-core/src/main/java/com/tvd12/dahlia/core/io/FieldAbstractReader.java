package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

abstract class FieldAbstractReader<T> implements FieldReader<T> {

    @Override
    public T read(
        FieldReaders readers,
        FileProxy file, FieldSetting setting) throws IOException {
        if (setting.isNullable()) {
            byte header = file.readByte();
            if ((header & 1) == 0) {
                return null;
            }
        }
        return readValue(readers, file, setting);
    }

    protected T readValue(
        FieldReaders readers,
        FileProxy file, FieldSetting setting) throws IOException {
        return readValue(file, setting);
    }

    protected T readValue(
        FileProxy file, FieldSetting setting) throws IOException {
        return null;
    }
}
