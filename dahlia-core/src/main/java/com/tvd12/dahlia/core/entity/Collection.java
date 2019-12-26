package com.tvd12.dahlia.core.entity;

import java.util.function.Consumer;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.tree.Tree;

import lombok.Getter;

public class Collection {

	@Getter
	protected long dataSize;
	@Getter
	protected final CollectionSetting setting;
	protected final Tree<Comparable, Record> indexById;
	
	public Collection(CollectionSetting setting) {
		this.setting = setting;
		this.indexById = new BTree<>();
	}
	
	public Record findById(Comparable id) {
		Record record = indexById.get(id);
		return record;
	}
	
	public void insert(Record record) {
		this.indexById.put(record.getId(), record);
	}
	
	public Record update(Record record) {
		return this.indexById.put(record.getId(), record);
	}
	
	public void forEach(Consumer<Record> consumer) {
		this.indexById.walk(e -> consumer.accept(e.getValue()));
	}
	
	public long increaseDataSize() {
		this.dataSize += setting.getRecordSize();
		return dataSize;
	}
	
	public int getId() {
		return setting.getCollectionId();
	}
	
	public String getName() {
		return setting.getCollectionName();
	}
	
	@Override
	public String toString() {
		return setting.getCollectionName();
	}

}
