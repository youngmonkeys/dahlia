package com.tvd12.dahlia.core.test.entity;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.core.storage.DatabaseStorage;
import com.tvd12.dahlia.core.storage.Storage;

public class DatabaseTest {

	public static void main(String[] args) {
		Storage storage = new Storage("data");
		DatabaseSetting databaseSetting = new DatabaseSetting();
		databaseSetting.setName("test");
		
		DatabaseStorage databaseStorage = storage.createDatabaseStorage(databaseSetting.getName());
		databaseStorage.mkdir();
		
		CollectionSetting collectionSetting = new CollectionSetting();
		collectionSetting.setName("test");
		Map<String, FieldSetting> fieldSettings = new HashMap<>();
		collectionSetting.setMappings(fieldSettings);
		FieldLongSetting fieldIdSetting = new FieldLongSetting();
		fieldIdSetting.setName("_id");
		fieldIdSetting.setType(DataType.LONG);
		fieldIdSetting.setNullable(true);
		fieldIdSetting.setDefaultValue(100L);
		fieldSettings.put(fieldIdSetting.getName(), fieldIdSetting);
		System.out.println(collectionSetting.toMap());
		
		CollectionStorage collectionStorage = databaseStorage.createCollectionStorage(collectionSetting.getName());
		collectionStorage.mkdir();
		collectionStorage.storeSetting(collectionSetting);
		
		CollectionSetting readSetting = collectionStorage.readSetting();
		System.out.println(readSetting.toMap());
		
		Database database = new Database("test");
		Collection collection = database.newCollection("test");
//		Record record = new Record(1);
//		record.set("value", "one");
//		collection.save(record);
//		System.out.println(collection.findById(1));
	}
}
