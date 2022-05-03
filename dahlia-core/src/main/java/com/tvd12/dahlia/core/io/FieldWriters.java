package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

import java.io.IOException;
import java.util.Map;

interface FieldWriters {

    void write(
        FileProxy file,
        String name,
        FieldSetting setting,
        Object value
    ) throws IOException;

    void write(
        FileProxy file,
        FieldSetting setting,
        Object value
    ) throws IOException;

    void write(
        FileProxy file,
        Map<String, FieldSetting> settings,
        EzyObject values
    ) throws IOException;
}
