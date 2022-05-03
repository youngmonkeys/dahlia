package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.ezyfox.util.EzyToMap;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tvd12.dahlia.core.constant.Constants.FIELD_ID;

@Setter
@Getter
public class CollectionSetting implements EzyToMap {

    protected int recordSize;
    protected int collectionId;
    protected String collectionName;
    protected FieldSetting id;
    protected Map<String, FieldSetting> fields;
    protected Map<String, IndexSetting> indexes;

    public CollectionSetting() {
        this.fields = new HashMap<>();
        this.indexes = new HashMap<>();
    }

    public void setFields(Map<String, FieldSetting> fields) {
        this.fields.putAll(fields);
        this.id = this.fields.remove(FIELD_ID);
    }

    public Map<String, FieldSetting> getAllFields() {
        Map<String, FieldSetting> all = new HashMap<>(fields);
        all.put(FIELD_ID, id);
        return all;
    }

    public void setIndexes(List<IndexSetting> indexes) {
        for (IndexSetting index : indexes) {
            this.indexes.put(index.getIndexName(), index);
        }
    }

    @Override
    public Map<Object, Object> toMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put(SettingFields.ID, collectionId);
        map.put(SettingFields.NAME, collectionName);
        Map<String, Map<Object, Object>> fieldsToMap = new HashMap<>();
        fieldsToMap.put(SettingFields.ID, id.toMap());
        for (String fieldName : fields.keySet()) {
            FieldSetting field = fields.get(fieldName);
            fieldsToMap.put(fieldName, field.toMap());
        }
        map.put(SettingFields.FIELDS, fieldsToMap);
        Map<Object, Object> indexesToMap = new HashMap<>();
        for (IndexSetting index : indexes.values()) {
            indexesToMap.putAll(index.toMap());
        }
        map.put(SettingFields.INDEXES, indexesToMap);
        return map;
    }
}
