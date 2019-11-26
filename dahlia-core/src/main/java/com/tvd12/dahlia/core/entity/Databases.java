package com.tvd12.dahlia.core.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Databases {

	protected final Map<Integer, Collection> collections;
	protected final Map<Integer, Database> databasesById;
	protected final Map<String, Database> databasesByName;
	
	public Databases() {
		this.collections = new ConcurrentHashMap<>();
		this.databasesById = new ConcurrentHashMap<>();
		this.databasesByName = new ConcurrentHashMap<>();
	}
	
	public Database getDatabase(int id) {
		Database database = databasesById.get(id);
		return database;
	}
	
	public Database getDatabase(String name) {
		Database database = databasesByName.get(name);
		return database;
	}
	
	public void addDatabase(Database database) {
		this.databasesById.put(database.getId(), database);
		this.databasesByName.put(database.getName(), database);
	}
	
	public List<Database> getDatabaseList() {
		return new ArrayList<>(databasesById.values());
	}
	
	public Collection getCollection(int id) {
		Collection collection = collections.get(id);
		return collection;
	}
	
	public void addCollection(Collection collection) {
		this.collections.put(collection.getId(), collection);
	}
	
	
}
