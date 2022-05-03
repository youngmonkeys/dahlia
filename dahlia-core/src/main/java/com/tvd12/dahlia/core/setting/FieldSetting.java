package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.ezyfox.util.EzyToMap;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public abstract class FieldSetting implements EzyToMap {

    protected boolean nullable;

    public abstract DataType getType();

    @Override
    public Map<Object, Object> toMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put(SettingFields.TYPE, getType().getName());
        map.put(SettingFields.NULLABLE, nullable);
        return map;
    }
}
