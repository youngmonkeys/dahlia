package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldArraySetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import java.io.IOException;

final class FieldArrayReader extends FieldAbstractReader<EzyArray> {

    private static final FieldArrayReader INSTANCE = new FieldArrayReader();

    private FieldArrayReader() {}

    public static FieldArrayReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected EzyArray readValue(
        FieldReaders readers,
        FileProxy file, FieldSetting setting) throws IOException {
        FieldArraySetting fs = (FieldArraySetting) setting;
        FieldSetting itemSetting = fs.getItem();
        int size = file.readShort();
        EzyArray array = EzyEntityFactory.newArray();
        for (int i = 0; i < size; ++i) {
            Object item = readers.readValue(file, itemSetting);
            array.add(item);
        }
        return array;
    }
}
