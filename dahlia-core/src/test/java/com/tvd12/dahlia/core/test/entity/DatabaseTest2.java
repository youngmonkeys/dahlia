package com.tvd12.dahlia.core.test.entity;

import com.tvd12.dahlia.constant.Keywords;
import com.tvd12.dahlia.core.DahliaCore;
import com.tvd12.dahlia.core.DahliaCoreLoader;
import com.tvd12.dahlia.core.command.*;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.setting.*;
import com.tvd12.dahlia.exception.CollectionExistedException;
import com.tvd12.dahlia.exception.DatabaseExistedException;
import com.tvd12.dahlia.exception.DuplicatedIdException;
import com.tvd12.dahlia.query.FindOptions;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.io.EzyMaps;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.tvd12.ezyfox.factory.EzyEntityFactory.newArrayBuilder;
import static com.tvd12.ezyfox.factory.EzyEntityFactory.newObjectBuilder;

public class DatabaseTest2 {

    public static void main(String[] args) {
        deleteDataDir();
        DahliaCoreLoader loader = new DahliaCoreLoader()
            .storageDirectory("data");
        DahliaCore dahlia = loader.load();
        CommandExecutor commandExecutor = dahlia.getCommandExecutor();

        DatabaseSetting databaseSetting = new DatabaseSetting();
        databaseSetting.setDatabaseName("hello");
        CommandCreateDatabase commandCreateDatabase = new CommandCreateDatabase(databaseSetting);
        Database database;
        try {
            database = commandExecutor.execute(commandCreateDatabase);
        } catch (DatabaseExistedException e) {
            database = dahlia.getDatabases().getDatabase("hello");
        }

        CollectionSetting collectionSetting = new CollectionSetting();
        collectionSetting.setCollectionId(1);
        collectionSetting.setCollectionName("test2");
        Map<String, FieldSetting> fieldSettings = new HashMap<>();
        FieldUuidSetting fieldIdSetting = new FieldUuidSetting();
        fieldIdSetting.setNullable(true);
        fieldSettings.put("_id", fieldIdSetting);

        FieldLongSetting fieldValueSetting = new FieldLongSetting();
        fieldValueSetting.setNullable(true);
        fieldValueSetting.setDefaultValue(300L);
        fieldSettings.put("value", fieldValueSetting);

        FieldTextSetting fieldNameSetting = new FieldTextSetting();
        fieldNameSetting.setNullable(false);
        fieldSettings.put("name", fieldNameSetting);

        collectionSetting.setFields(fieldSettings);

        IndexSetting nameIndexSetting = new IndexSetting("nameIndex", EzyMaps.newHashMap("name", true));
        collectionSetting.setIndexes(Lists.newArrayList(nameIndexSetting));

        System.out.println(collectionSetting.toMap());

        CommandCreateCollection commandCreateCollection = new CommandCreateCollection(database.getId(), collectionSetting);
        Collection collection;
        try {
            collection = commandExecutor.execute(commandCreateCollection);
        } catch (CollectionExistedException e) {
            collection = database.getCollection("test2");
        }
        EzyObject insertOneData = newObjectBuilder()
            .append("value", 323L)
            .append("name", "dungtv")
            .build();
        CommandInsertOne commandInsertOne = new CommandInsertOne(collection.getId(), insertOneData);
        try {
            EzyObject insertOneResult = commandExecutor.execute(commandInsertOne);
            System.out.println("insert one result: " + insertOneResult);
        } catch (DuplicatedIdException ignored) {
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
                .append(newObjectBuilder().append("value", 323))
            )
            .build();
        FindOptions options = new FindOptions().setSkip(0).setLimit(10);
        CommandFind commandFind = new CommandFind(collection.getId(), query3, options.toObject());
        EzyArray findResult = commandExecutor.execute(commandFind);
        System.out.println("findResult = " + findResult);

        Long size = dahlia.execute(new CommandCount(collection.getId()));
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
