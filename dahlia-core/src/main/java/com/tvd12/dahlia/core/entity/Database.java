package com.tvd12.dahlia.core.entity;

import com.tvd12.dahlia.core.setting.DatabaseSetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    protected final DatabaseSetting setting;
    protected final Map<Integer, Collection> collectionsById;
    protected final Map<String, Collection> collectionsByName;

    public Database(DatabaseSetting setting) {
        this.setting = setting;
        this.collectionsById = new ConcurrentHashMap<>();
        this.collectionsByName = new ConcurrentHashMap<>();
    }

    public void addCollection(Collection collection) {
        this.collectionsById.put(collection.getId(), collection);
        this.collectionsByName.put(collection.getName(), collection);
    }

    public Collection getCollection(int id) {
        return collectionsById.get(id);
    }

    public Collection getCollection(String name) {
        return collectionsByName.get(name);
    }

    public List<Collection> getCollectionList() {
        return new ArrayList<>(collectionsById.values());
    }

    public int getId() {
        return setting.getDatabaseId();
    }

    public String getName() {
        return setting.getDatabaseName();
    }

    @Override
    public String toString() {
        return setting.getDatabaseName();
    }
}
