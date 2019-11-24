package com.tvd12.dahlia.core.test.entity;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.DahliaCore;
import com.tvd12.dahlia.core.DahliaCoreLoader;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.command.CreateCollection;
import com.tvd12.dahlia.core.command.CreateDatabase;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.exception.DatabaseExistedException;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;

public class DatabaseTest2 {

	public static void main(String[] args) {
		DahliaCoreLoader loader = new DahliaCoreLoader()
				.storageDirectory("data");
		DahliaCore dahlia = loader.load();
		CommandExecutor commandExecutor = dahlia.getCommandExecutor();
		
		DatabaseSetting databaseSetting = new DatabaseSetting();
		databaseSetting.setDatabaseName("hello");
		CreateDatabase createDatabase = new CreateDatabase(databaseSetting);
		Database database = null;
		try {
			database = commandExecutor.execute(createDatabase);
		}
		catch (DatabaseExistedException e) {
			database = dahlia.getDatabases().getDatabase("hello");
		}
		
		CollectionSetting collectionSetting = new CollectionSetting();
		collectionSetting.setCollectionId(1);
		collectionSetting.setCollectionName("test");
		Map<String, FieldSetting> fieldSettings = new HashMap<>();
		collectionSetting.setFields(fieldSettings);
		FieldLongSetting fieldIdSetting = new FieldLongSetting();
		fieldIdSetting.setName("_id");
		fieldIdSetting.setType(DataType.LONG);
		fieldIdSetting.setNullable(true);
		fieldIdSetting.setDefaultValue(100L);
		fieldSettings.put(fieldIdSetting.getName(), fieldIdSetting);
		System.out.println(collectionSetting.toMap());
		CreateCollection createCollection = new CreateCollection(database.getId(), collectionSetting);
		commandExecutor.execute(createCollection);
//		
//		RecordSizeReader recordSizeReader = new RecordSizeReader();
//		int recordSize = recordSizeReader.read(fieldSettings);
//		System.out.println("recordSize: " + recordSize);
//		
//		CollectionStorage collectionStorage = databaseStorage.createCollectionStorage(collectionSetting);
//		collectionStorage.mkdir();
//		collectionStorage.storeSetting(collectionSetting);
//		
//		CollectionSetting readSetting = collectionStorage.readSetting();
//		System.out.println(readSetting.toMap());
		
//		Database database = new Database("test");
//		Collection collection = database.newCollection("test");
//		Record record = new Record(1);
//		record.set("value", "one");
//		collection.save(record);
//		System.out.println(collection.findById(1));
	}
}
