package com.tvd12.dahlia.query;

import com.tvd12.dahlia.constant.OptionFields;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Getter
public class FindOptions {

    protected int skip;
    protected int limit = 25;
    protected Map<String, Boolean> sortBy;

    public FindOptions setSkip(int skip) {
        this.skip = skip;
        return this;
    }

    public FindOptions setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public FindOptions sortBy(String field) {
        return sortBy(field, true);
    }

    public FindOptions sortBy(String field, boolean asc) {
        if (sortBy == null) {
            sortBy = new HashMap<>();
        }
        this.sortBy.put(field, asc);
        return this;
    }

    public Map<String, Boolean> getSortBy() {
        return sortBy != null ? sortBy : new HashMap<>();
    }

    public EzyObject toObject() {
        EzyObject obj = EzyEntityFactory.newObject();
        obj.put(OptionFields.SKIP, skip);
        obj.put(OptionFields.LIMIT, limit);
        EzyObject sort = EzyEntityFactory.newObject();
        for (Entry<String, Boolean> field : sortBy.entrySet()) {
            sort.put(field.getKey(), field.getValue());
        }
        obj.put(OptionFields.SORT, sort);
        return obj;
    }
}
