package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldObjectSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

import java.io.IOException;
import java.util.Map;

final class FieldObjectWriter extends FieldAbstractWriter<EzyObject> {

    private static final FieldObjectWriter INSTANCE = new FieldObjectWriter();

    private FieldObjectWriter() {}

    public static FieldObjectWriter getInstance() {
        return INSTANCE;
    }

    @Override
    protected void writeValue(
        FieldWriters writers,
        FileProxy file,
        FieldSetting setting, EzyObject value) throws IOException {
        FieldObjectSetting fs = (FieldObjectSetting) setting;
        Map<String, FieldSetting> fieldSettings = fs.getFields();
        writers.write(file, fieldSettings, value);
    }
}
