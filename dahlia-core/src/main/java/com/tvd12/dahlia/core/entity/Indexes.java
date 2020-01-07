package com.tvd12.dahlia.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.util.Pair;

public class Indexes {

	protected final Map<Object, Index> indexes;
	protected final Map<Object, Index> indexesByFields;
	
	public Indexes() {
		this.indexes = new ConcurrentHashMap<>();
		this.indexesByFields = new ConcurrentHashMap<>();
	}
	
	public void addIndex(Object key, Index index) {
		indexes.put(key, index);
		if(key instanceof Pair) {
			indexesByFields.put(((Pair)key).getKey(), index);
		}
		else {
			List<String> fields = getFields(key);
			indexesByFields.put(fields, index);
		}
	}
	
	public Index getIndex(Object key) {
		Index index = indexes.get(key);
		return index;
	}
	
	public Index getIndexByFields(Object fields) {
		Index index = null;
		if(fields instanceof String)
			index = indexesByFields.get(fields);
		else
			index = indexesByFields.get(fields);
		return index;
	}
	
	protected List<String> getFields(Object key) {
		List<Pair<String, Boolean>> pairs = (List)key;
		List<String> fields = new ArrayList<>();
		for(Pair<String, Boolean> pair : pairs) 
			fields.add(pair.getKey());
		return fields;
	}
	
}
