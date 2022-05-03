package com.tvd12.dahlia.core.constant;

import com.tvd12.dahlia.constant.SettingFields;

public final class Constants {

    public static final String FIELD_ID = SettingFields.ID;

    public static final String PREFIX_FILE = "file:";
    public static final String PREFIX_CLASSPATH = "classpath:";

    public static final int MAX_UTF8_CHAR_BYTES = 4;
    public static final int MAX_STRING_SIZE_BYTES = 4;
    public static final int MAX_UUID_SIZE = 36;
    public static final int MAX_BIGDECIMAL_SIZE = 32;

    public static final int RECORD_HEADER_SIZE = 1;

    public static final String MODE_READ_WRITE = "rw";

    public static final String RESULT_FIELD_EXISTED = "existed";

    public static final String DIRECTORY_DATABASES = "databases";

    public static final String FILE_RUNTIME_DATA = "runtime.dat";
    public static final String FILE_SETTINGS_DATA = "settings.dat";
    public static final String FILE_RECORDS_DATA = "records.dat";

    private Constants() {
    }

}
