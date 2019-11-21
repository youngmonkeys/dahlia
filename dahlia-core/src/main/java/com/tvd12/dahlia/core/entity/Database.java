package com.tvd12.dahlia.core.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.core.setting.CollectionSetting;

public class Database {

	protected final String name;
	protected final Map<String, Collection> collections;
	
	public Database(String name) {
		this.name = name;
		this.collections = new ConcurrentHashMap<>();
	}
	
	public void addCollection(Collection collection) {
		this.collections.put(collection.getName(), collection);
	}
	
	public Collection getCollection(String name) {
		Collection collection = collections.get(name);
		return collection;
	}
	
	public Collection newCollection(CollectionSetting setting) {
		Collection collection = new Collection(setting);
		addCollection(collection);
		return collection;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
