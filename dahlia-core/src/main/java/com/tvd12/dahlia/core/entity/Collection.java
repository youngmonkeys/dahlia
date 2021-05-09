package com.tvd12.dahlia.core.entity;

import com.tvd12.dahlia.core.collection.PartitionalMap;
import com.tvd12.dahlia.core.function.IdConsumer;
import com.tvd12.dahlia.core.function.RecordConsumer;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.IndexSetting;

import lombok.Getter;

@SuppressWarnings("rawtypes")
public class Collection {

	@Getter
	protected long dataSize;
	@Getter
	protected final Index idIndex;
	@Getter
	protected final Indexes indexes;
	@Getter
	protected final CollectionSetting setting;
	protected final PartitionalMap<Comparable, Record> recordById;
	
	public Collection(CollectionSetting setting) {
		this.setting = setting;
		this.recordById = new PartitionalMap<>();
		this.indexes = new Indexes();
		this.idIndex = indexes.getIdIndex();
	}
	
	public Record findById(Comparable id) {
		Record record = recordById.get(id);
		return record;
	}
	
	public void insert(Record record) {
		this.recordById.put(record.getId(), record);
		this.idIndex.insert(record.getId(), record.getId());
	}
	
	public Record update(Record record) {
		return this.recordById.put(record.getId(), record);
	}
	
	public Record remove(Comparable id) {
		Record record = this.recordById.remove(id);
		this.idIndex.remove(id);
		return record;
	}
	
	public void forEach(RecordConsumer consumer) {
		this.getIdIndex().forEach(new IdConsumer() {
			@Override
			public void accept(Comparable id) {
				Record record = recordById.get(id);
				consumer.accept(record);
			}
			@Override
			public boolean next() {
				return consumer.next();
			}
		});
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
	
	public long size() {
		return recordById.sizeLong();
	}
	
	public Index getIndex(IndexSetting setting) {
		return indexes.getIndexBySetting(setting);
	}
	
	public void addIndex(Index index) {
		this.indexes.addIndex(index);
	}
	
	@Override
	public String toString() {
		return setting.getCollectionName();
	}

}
