package com.tvd12.dahlia.core.entity;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.setting.IndexSetting;

public class Indexes {

	protected final Map<Object, Index> indexes;
	protected final Map<Object, Index> indexesByName;
	
	public Indexes() {
		this.indexes = new HashMap<>();
		this.indexesByName = new HashMap<>();
	}
	
	public void addIndex(Index index) {
		IndexSetting setting = index.getSetting();
		String name = setting.getName();
		Map<String, Boolean> fields = setting.getFields();
		this.indexes.put(fields, index);
		this.indexesByName.put(name, index);
	}
	
	public Index getIndex(Object key) {
		Index index = indexes.get(key);
		return index;
	}
	
}
