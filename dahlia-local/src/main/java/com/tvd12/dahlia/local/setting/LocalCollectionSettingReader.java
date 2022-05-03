package com.tvd12.dahlia.local.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.*;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.stream.EzyAnywayInputStreamLoader;
import com.tvd12.ezyfox.stream.EzyInputStreamLoader;
import com.tvd12.ezyfox.stream.EzyInputStreamReader;
import com.tvd12.ezyfox.stream.EzySimpleInputStreamReader;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalCollectionSettingReader {

    protected final EzyInputStreamLoader inputStreamLoader;
    protected final EzyInputStreamReader inputStreamReader;

    public LocalCollectionSettingReader() {
        this.inputStreamLoader = EzyAnywayInputStreamLoader.builder()
            .build();
        this.inputStreamReader = EzySimpleInputStreamReader.builder()
            .build();
    }

    public CollectionSetting readFileSetting(File file) {
        try {
            InputStream inputStream = new FileInputStream(file);
            return readInputStreamSetting(inputStream);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public CollectionSetting readFileSetting(String filePath) {
        InputStream inputStream = inputStreamLoader.load(filePath);
        return readInputStreamSetting(inputStream);
    }

    public CollectionSetting readInputStreamSetting(InputStream inputStream) {
        String json = inputStreamReader.readString(inputStream, EzyStrings.UTF_8);
        return readJsonSetting(json);
    }

    public CollectionSetting readJsonSetting(String json) {
        JSONObject rawSetting = new JSONObject(json);
        CollectionSetting setting = new CollectionSetting();
        String collectionName = null;
        if (rawSetting.has(SettingFields.NAME)) {
            collectionName = rawSetting.getString(SettingFields.NAME);
        } else {
            throw new IllegalArgumentException("collection 'name' is required");
        }
        setting.setCollectionName(collectionName);
        JSONObject fieldSettings = null;
        if (rawSetting.has(SettingFields.FIELDS)) {
            fieldSettings = rawSetting.getJSONObject(SettingFields.FIELDS);
        } else {
            throw new IllegalArgumentException("'fields' is required");
        }
        setting.setFields(readFieldSettings(fieldSettings));
        if (rawSetting.has(SettingFields.INDEXES)) {
            JSONObject indexSettings = rawSetting.getJSONObject(SettingFields.INDEXES);
            setting.setIndexes(readIndexSettings(indexSettings));
        }
        System.out.println("settings: " + setting.toMap());
        return setting;
    }

    protected Map<String, FieldSetting> readFieldSettings(JSONObject settings) {
        Map<String, FieldSetting> answer = new HashMap<>();
        for (String fieldName : settings.keySet()) {
            JSONObject setting = settings.getJSONObject(fieldName);
            DataType type = null;
            if (setting.has(SettingFields.TYPE)) {
                type = DataType.valueOfName(setting.getString(SettingFields.TYPE));
            } else {
                throw new IllegalArgumentException("'type' is required at field: " + fieldName);
            }
            answer.put(fieldName, readFieldSetting(type, setting));
        }
        return answer;
    }

    protected FieldSetting readFieldSetting(DataType type, JSONObject setting) {
        FieldSetting field = null;
        if (type == DataType.BOOLEAN) {
            field = readFieldBooleanSetting(setting);
        } else if (type == DataType.BYTE) {
            field = readFieldByteSetting(setting);
        } else if (type == DataType.DOUBLE) {
            field = readFieldDoubleSetting(setting);
        } else if (type == DataType.FLOAT) {
            field = readFieldFloatSetting(setting);
        } else if (type == DataType.INTEGER) {
            field = readFieldIntegerSetting(setting);
        } else if (type == DataType.LONG) {
            field = readFieldLongSetting(setting);
        } else if (type == DataType.SHORT) {
            field = readFieldShortSetting(setting);
        } else if (type == DataType.TEXT) {
            field = readFieldTextSetting(setting);
        } else if (type == DataType.OBJECT) {
            field = readFieldObjectSetting(setting);
        } else if (type == DataType.ARRAY) {
            field = readFieldArraySetting(setting);
        } else if (type == DataType.UUID) {
            field = readFieldUuidSetting(setting);
        } else {
            field = readFieldBigDecimalSetting(setting);
        }
        if (setting.has(SettingFields.NULLABLE)) {
            field.setNullable(setting.getBoolean(SettingFields.NULLABLE));
        }
        return field;
    }

    protected FieldSetting readFieldBooleanSetting(JSONObject setting) {
        FieldBooleanSetting field = new FieldBooleanSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue(setting.getBoolean(SettingFields.DEFAULT));
        }
        return field;
    }

    protected FieldSetting readFieldByteSetting(JSONObject setting) {
        FieldByteSetting field = new FieldByteSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue((byte) setting.getInt(SettingFields.DEFAULT));
        }
        return field;
    }

    protected FieldSetting readFieldDoubleSetting(JSONObject setting) {
        FieldDoubleSetting field = new FieldDoubleSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue(setting.getDouble(SettingFields.DEFAULT));
        }
        return field;
    }

    protected FieldSetting readFieldFloatSetting(JSONObject setting) {
        FieldFloatSetting field = new FieldFloatSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue(setting.getFloat(SettingFields.DEFAULT));
        }
        return field;
    }

    protected FieldSetting readFieldIntegerSetting(JSONObject setting) {
        FieldIntegerSetting field = new FieldIntegerSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue(setting.getInt(SettingFields.DEFAULT));
        }
        if (setting.has(SettingFields.MAX_VALUE)) {
            field.setMaxValue(setting.getInt(SettingFields.MAX_VALUE));
        }
        return field;
    }

    protected FieldSetting readFieldLongSetting(JSONObject setting) {
        FieldLongSetting field = new FieldLongSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue(setting.getLong(SettingFields.DEFAULT));
        }
        return field;
    }

    protected FieldSetting readFieldShortSetting(JSONObject setting) {
        FieldShortSetting field = new FieldShortSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue((short) setting.getInt(SettingFields.DEFAULT));
        }
        return field;
    }

    protected FieldSetting readFieldTextSetting(JSONObject setting) {
        FieldTextSetting field = new FieldTextSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue(setting.getString(SettingFields.DEFAULT));
        }
        if (setting.has(SettingFields.MAX_SIZE)) {
            field.setMaxSize(setting.getInt(SettingFields.MAX_VALUE));
        }
        return field;
    }

    protected FieldSetting readFieldObjectSetting(JSONObject setting) {
        FieldObjectSetting field = new FieldObjectSetting();
        if (setting.has(SettingFields.FIELDS)) {
            JSONObject fieldSettings = setting.getJSONObject(SettingFields.FIELDS);
            Map<String, FieldSetting> fields = readFieldSettings(fieldSettings);
            field.setFields(fields);
        } else {
            throw new IllegalArgumentException("'fields' is required");
        }
        return field;
    }

    protected FieldSetting readFieldArraySetting(JSONObject setting) {
        FieldArraySetting field = new FieldArraySetting();
        if (setting.has(SettingFields.MAX_SIZE)) {
            field.setMaxSize(setting.getInt(SettingFields.MAX_SIZE));
        }
        if (setting.has(SettingFields.ITEM)) {
            JSONObject itemSetting = setting.getJSONObject(SettingFields.ITEM);
            DataType itemType = null;
            if (itemSetting.has(SettingFields.TYPE)) {
                itemType = DataType.valueOfName(itemSetting.getString(SettingFields.TYPE));
            } else {
                throw new IllegalArgumentException("item 'type' is required");
            }
            FieldSetting item = readFieldSetting(itemType, itemSetting);
            field.setItem(item);
        } else {
            throw new IllegalArgumentException("'item' field is required");
        }
        return field;
    }

    protected FieldSetting readFieldUuidSetting(JSONObject setting) {
        FieldUuidSetting field = new FieldUuidSetting();
        return field;
    }

    protected FieldSetting readFieldBigDecimalSetting(JSONObject setting) {
        FieldBigDecimalSetting field = new FieldBigDecimalSetting();
        if (setting.has(SettingFields.DEFAULT)) {
            field.setDefaultValue(setting.getBigDecimal(SettingFields.DEFAULT));
        }
        return field;
    }

    protected List<IndexSetting> readIndexSettings(JSONObject settings) {
        List<IndexSetting> answer = new ArrayList<>();
        for (String indexName : settings.keySet()) {
            JSONObject fieldSettings = settings.getJSONObject(indexName);
            Map<String, Boolean> fields = readIndexFieldSettings(fieldSettings);
            answer.add(new IndexSetting(indexName, fields));
        }
        return answer;
    }

    protected Map<String, Boolean> readIndexFieldSettings(JSONObject fields) {
        Map<String, Boolean> answer = new HashMap<>();
        for (String fieldName : fields.keySet()) {
            boolean asc = fields.getBoolean(fieldName);
            answer.put(fieldName, asc);
        }
        return answer;
    }
}
