package com.tvd12.dahlia.core.setting;

final class FieldObjectSizeReader extends FieldSizeReader<FieldObjectSetting> {

    private static final FieldObjectSizeReader INSTANCE = new FieldObjectSizeReader();

    private FieldObjectSizeReader() {}

    public static FieldObjectSizeReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected int readDataSize(FieldObjectSetting setting) {
        return Short.BYTES;
    }

    @Override
    protected int readPropertiesSize(FieldSizeReaders readers, FieldObjectSetting setting) {
        return readers.read(setting.getFields());
    }
}