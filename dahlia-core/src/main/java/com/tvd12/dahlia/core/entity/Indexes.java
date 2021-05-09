package com.tvd12.dahlia.core.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tvd12.dahlia.core.setting.IndexSetting;

public class Indexes {

	protected final Map<String, Index> indexesByName;
	protected final Map<IndexSetting, Index> indexBySetting;
	protected final Map<String, Set<Index>> indexesByFieldName;
	
	public Indexes() {
		this.indexesByName = new HashMap<>();
		this.indexBySetting = new HashMap<>();
		this.indexesByFieldName = new HashMap<>();
		this.addIndex(Index.ofId());
	}
	
	public void addIndex(Index index) {
		IndexSetting setting = index.getSetting();
		String indexName = setting.getIndexName();
		Map<String, Boolean> fields = setting.getFields();
		this.indexesByName.put(indexName, index);
		this.indexBySetting.put(setting, index);
		for(String fieldName : fields.keySet()) {
			this.indexesByFieldName
				.computeIfAbsent(fieldName, k -> new HashSet<>())
				.add(index);
		}
	}
	
	public Index getIndexBySetting(IndexSetting setting) {
		Index index = indexBySetting.get(setting);
		return index;
	}
	
	public Index getIdIndex() {
		return getIndexBySetting(IndexSetting.ID_SETTING);
	}
	
}
