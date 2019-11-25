package com.tvd12.dahlia.core.test.entity;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.DahliaCore;
import com.tvd12.dahlia.core.DahliaCoreLoader;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.command.CreateCollection;
import com.tvd12.dahlia.core.command.CreateDatabase;
import com.tvd12.dahlia.core.command.FindOne;
import com.tvd12.dahlia.core.command.InsertOne;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.exception.CollectionExistedException;
import com.tvd12.dahlia.core.exception.DatabaseExistedException;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class DatabaseTest {

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
		Collection collection = null;
		try {
			collection = commandExecutor.execute(createCollection);
		}
		catch (CollectionExistedException e) {
			collection = database.getCollection("test");
		}
		EzyObject insertOneData = EzyEntityFactory.newObjectBuilder()
				.append("_id", 2L)
				.build();
		InsertOne insertOne = new InsertOne(collection.getId(), insertOneData);
		EzyObject insertOneResult = commandExecutor.execute(insertOne);
		System.out.println("insert one result: " + insertOneResult);
		
		FindOne findOne = new FindOne(collection.getId(), 2L);
		EzyObject findOneResult = commandExecutor.execute(findOne);
		System.out.println("findOneResult: " + findOneResult);
	}
}
