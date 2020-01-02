package com.tvd12.dahlia.core.test.entity;

import static com.tvd12.ezyfox.factory.EzyEntityFactory.newArrayBuilder;
import static com.tvd12.ezyfox.factory.EzyEntityFactory.newObjectBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tvd12.dahlia.constant.Keywords;
import com.tvd12.dahlia.core.DahliaCore;
import com.tvd12.dahlia.core.DahliaCoreLoader;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.command.Count;
import com.tvd12.dahlia.core.command.CreateCollection;
import com.tvd12.dahlia.core.command.CreateDatabase;
import com.tvd12.dahlia.core.command.Find;
import com.tvd12.dahlia.core.command.InsertOne;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.exception.CollectionExistedException;
import com.tvd12.dahlia.core.exception.DatabaseExistedException;
import com.tvd12.dahlia.core.exception.DuplicatedIdException;
import com.tvd12.dahlia.core.query.FindOptions;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.setting.FieldTextSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class DatabaseTest {

	public static void main(String[] args) {
//		deleteDataDir();
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
		FieldLongSetting fieldIdSetting = new FieldLongSetting();
		fieldIdSetting.setNullable(true);
		fieldIdSetting.setDefaultValue(100L);
		fieldSettings.put("_id", fieldIdSetting);
		
		FieldLongSetting fieldValueSetting = new FieldLongSetting();
		fieldValueSetting.setNullable(true);
		fieldValueSetting.setDefaultValue(300L);
		fieldSettings.put("value", fieldValueSetting);
		
		FieldTextSetting fieldNameSetting = new FieldTextSetting();
		fieldNameSetting.setNullable(false);
		fieldSettings.put("name", fieldNameSetting);
		
		collectionSetting.setFields(fieldSettings);
		
		System.out.println(collectionSetting.toMap());
		
		CreateCollection createCollection = new CreateCollection(database.getId(), collectionSetting);
		Collection collection = null;
		try {
			collection = commandExecutor.execute(createCollection);
		}
		catch (CollectionExistedException e) {
			collection = database.getCollection("test");
		}
		EzyObject insertOneData = newObjectBuilder()
				.append("_id", 2L)
				.append("value", 323L)
				.append("name", "dungtv")
				.build();
		InsertOne insertOne = new InsertOne(collection.getId(), insertOneData);
		try {
			EzyObject insertOneResult = commandExecutor.execute(insertOne);
			System.out.println("insert one result: " + insertOneResult);
		}
		catch (DuplicatedIdException e) {
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		long v1 = 1;
		double v2 = 1.1;
		System.out.println(v2 == v1);
		
//		EzyObject query1 = newObjectBuilder()
//				.append("_id", newObjectBuilder().append(Keywords.LESS_THAN_EQUAL, 3L))
//				.build();
//		FindOne findOne = new FindOne(collection.getId(), query1);
//		EzyObject findOneResult = commandExecutor.execute(findOne);
//		System.out.println("findOneResult: " + findOneResult);
		
//		EzyObject query2 = newObjectBuilder()
//				.append(Keywords.OR, newArrayBuilder()
//						.append(newObjectBuilder().append("_id", newObjectBuilder().append(Keywords.LESS_THAN_EQUAL, 3L)))
//						.append(newObjectBuilder().append("value", 223))
//						)
//				.build();
		EzyObject query3 = newObjectBuilder()
				.append(Keywords.OR, newArrayBuilder()
						.append(newObjectBuilder().append(Keywords.LESS_THAN_EQUAL, newObjectBuilder().append("_id", 3L)))
						.append(newObjectBuilder().append("value", 223))
						)
				.build();
		FindOptions options = new FindOptions().setSkip(0).setLimit(10);
		Find find = new Find(collection.getId(), query3, options);
		List<EzyObject> findResult = commandExecutor.execute(find);
		System.out.println("findResult = " + findResult);
		
		Long size = dahlia.execute(new Count(collection.getId()));
		System.out.println("size: " + size);
	}
	
	public static void deleteDataDir() {
		deleteDataFile(new File("data"));
	}
	
	public static void deleteDataFile(File file) {
		if(!file.exists())
			return;
		if(file.isFile()) {
			file.delete();
		}
		else {
			for(File dir : file.listFiles())
				deleteDataFile(dir);
		}
		file.delete();
	}
}
