package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

interface FieldWriter<T> {

    void write(
        FieldWriters writers,
        FileProxy file,
        FieldSetting setting, T value
    ) throws IOException;
}
