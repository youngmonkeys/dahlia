package com.tvd12.dahlia.local.test;

import com.tvd12.dahlia.ICollection;
import com.tvd12.dahlia.IDatabase;
import com.tvd12.dahlia.constant.Keywords;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.exception.CollectionExistedException;
import com.tvd12.dahlia.exception.DatabaseExistedException;
import com.tvd12.dahlia.local.LocalDahlia;
import com.tvd12.dahlia.query.FindOptions;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

import java.io.File;

import static com.tvd12.ezyfox.factory.EzyEntityFactory.newArrayBuilder;
import static com.tvd12.ezyfox.factory.EzyEntityFactory.newObjectBuilder;

public class SecondTest {

    public static void main(String[] args) {
        deleteDataDir();
        LocalDahlia dahlia = new LocalDahlia("data");

        DatabaseSetting databaseSetting = new DatabaseSetting();
        databaseSetting.setDatabaseName("hello");
        IDatabase database;
        try {
            database = dahlia.createDatabase(databaseSetting);
        } catch (DatabaseExistedException e) {
            database = dahlia.getDatabase("hello");
        }

        ICollection collection;
        try {
            collection = database.createCollection("classpath:hello_test_setting2.json");
        } catch (CollectionExistedException e) {
            collection = database.getCollection("test");
        }
        EzyObject insertOneData = newObjectBuilder()
            .append("value", 323L)
            .append("name", "dungtv")
            .build();
        try {
            EzyObject insertOneResult = collection.insert(insertOneData);
            System.out.println("insert one result: " + insertOneResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                .append(newObjectBuilder().append("value", 323L))
            )
            .build();
        FindOptions options = new FindOptions().setSkip(0).setLimit(10);
        EzyArray findResult = collection.find(query3, options);
        System.out.println("findResult = " + findResult);

        long size = collection.count();
        System.out.println("size: " + size);
    }

    public static void deleteDataDir() {
        deleteDataFile(new File("data"));
    }

    public static void deleteDataFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        } else {
            //noinspection ConstantConditions
            for (File dir : file.listFiles()) {
                deleteDataFile(dir);
            }
        }
        //noinspection ResultOfMethodCallIgnored
        file.delete();
    }

}
