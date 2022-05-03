package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

interface FieldReader<T> {

    T read(FieldReaders readers,
           FileProxy file,
           FieldSetting setting
    ) throws IOException;
}
