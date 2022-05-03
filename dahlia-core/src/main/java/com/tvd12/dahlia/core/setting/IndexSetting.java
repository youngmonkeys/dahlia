package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.ezyfox.util.EzyToMap;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class IndexSetting implements EzyToMap {

    protected final String indexName;
    protected final Map<String, Boolean> fields;

    public static final String ID_INDEX_NAME = "id";
    public static final boolean ASCENDING_INDEX = true;
    public static final IndexSetting ID_SETTING = new IndexSetting(
        ID_INDEX_NAME,
        Collections.singletonMap(SettingFields.ID, ASCENDING_INDEX)
    );

    @Override
    public Map<Object, Object> toMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put(indexName, fields);
        return map;
    }
}
