package com.tvd12.dahlia.local.test;

import static com.tvd12.ezyfox.factory.EzyEntityFactory.newArrayBuilder;
import static com.tvd12.ezyfox.factory.EzyEntityFactory.newObjectBuilder;

import java.io.File;

import com.tvd12.dahlia.ICollection;
import com.tvd12.dahlia.IDatabase;
import com.tvd12.dahlia.constant.Keywords;
import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.exception.CollectionExistedException;
import com.tvd12.dahlia.exception.DatabaseExistedException;
import com.tvd12.dahlia.exception.DuplicatedIdException;
import com.tvd12.dahlia.local.LocalDahlia;
import com.tvd12.dahlia.query.FindOptions;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public class FirstTest {
	
	public static void main(String[] args) {
		deleteDataDir();
		LocalDahlia dahlia = new LocalDahlia("data");
		
		DatabaseSetting databaseSetting = new DatabaseSetting();
		databaseSetting.setDatabaseName("hello");
		IDatabase database = null;
		try {
			database = dahlia.createDatabase(databaseSetting);
		}
		catch (DatabaseExistedException e) {
			database = dahlia.getDatabase("hello");
		}
		
		ICollection collection = null;
		try {
			collection = database.createCollection("classpath:hello_test_setting.json");
		}
		catch (CollectionExistedException e) {
			collection = database.getCollection("test");
		}
		EzyObject insertOneData1 = newObjectBuilder()
				.append(SettingFields.ID, 2)
				.append("value", 323L)
				.append("name", "dungtv")
				.build();
		try {
			EzyObject insertOneResult = collection.insert(insertOneData1);
			System.out.println("insert one result 1: " + insertOneResult);
		}
		catch (DuplicatedIdException e) {
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		EzyObject insertOneData2 = newObjectBuilder()
				.append(SettingFields.ID, 3)
				.append("value", 325L)
				.append("name", "dungtv")
				.build();
		try {
			EzyObject insertOneResult = collection.insert(insertOneData2);
			System.out.println("insert one result 2: " + insertOneResult);
		}
		catch (DuplicatedIdException e) {
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		EzyObject insertOneData3 = newObjectBuilder()
				.append(SettingFields.ID, 4)
				.append("value", 321L)
				.append("name", "dungtv")
				.build();
		try {
			EzyObject insertOneResult = collection.insert(insertOneData3);
			System.out.println("insert one result 3: " + insertOneResult);
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
//		CommandFindOne findOne = new CommandFindOne(collection.getId(), query1);
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
						.append(newObjectBuilder().append(Keywords.LESS_THAN_EQUAL, newObjectBuilder().append("_id", 100L)))
						.append(newObjectBuilder().append("value", 323L))
						)
				.build();
		FindOptions options = new FindOptions()
				.setSkip(0)
				.setLimit(10)
				.sortBy("value");
		EzyArray findResult = collection.find(query3, options);
		System.out.println("findResult = " + findResult);
		
		Long size = collection.count();
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
