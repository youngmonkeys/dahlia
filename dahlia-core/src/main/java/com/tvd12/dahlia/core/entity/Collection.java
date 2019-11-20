package com.tvd12.dahlia.core.entity;

import com.tvd12.dahlia.core.btree.BTree;
import com.tvd12.dahlia.core.tree.Tree;

import lombok.Getter;

public class Collection {

	@Getter
	protected final String name;
	protected final Tree<Comparable, Record> indexById;
	
	public Collection(String name) {
		this.name = name;
		this.indexById = new BTree<>();
	}
	
	public void save(Record record) {
		this.indexById.put(record.getId(), record);
	}
	
	public Record findById(Comparable id) {
		Record record = indexById.get(id);
		return record;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
