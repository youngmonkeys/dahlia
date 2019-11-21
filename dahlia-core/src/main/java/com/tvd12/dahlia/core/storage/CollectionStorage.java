package com.tvd12.dahlia.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.tvd12.dahlia.core.codec.RecordWriter;
import com.tvd12.dahlia.core.codec.SettingCollectionDeserializer;
import com.tvd12.dahlia.core.codec.SettingCollectionSerializer;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.io.Directories;
import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.io.FileRandomAccessProxy;
import com.tvd12.dahlia.core.io.FileReaders;
import com.tvd12.dahlia.core.io.FileWriters;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.codec.MsgPackBytesSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.entity.EzyObject;

import static com.tvd12.dahlia.core.constant.Constants.*;

public class CollectionStorage {

	protected final String databaseName;
	protected final String collectionName;
	protected final String storageDirectory;
	protected final Path directoryPath;

	protected final CollectionRecordStore recordStore;
	protected final CollectionSettingStorage settingStorage;
	protected final SettingCollectionSerializer settingSerializer;
	protected final SettingCollectionDeserializer settingDeserializer;
	
	public CollectionStorage(
			String collectionName, 
			String databaseName, String storageDirectory) {
		this.collectionName = collectionName;
		this.databaseName = databaseName;
		this.storageDirectory = storageDirectory;
		this.settingSerializer = 
				new SettingCollectionSerializer(new MsgPackBytesSerializer());
		this.settingDeserializer = 
				new SettingCollectionDeserializer(new MsgPackSimpleDeserializer());
		this.directoryPath = Paths.get(
				storageDirectory,
				Constants.DIRECTORY_DATABASES, databaseName, collectionName);
		this.recordStore = newRecordStorage();
		this.settingStorage = newSettingStorage();
	}
	
	protected CollectionRecordStore newRecordStorage() {
		return new CollectionRecordStore(directoryPath);
	}
	
	protected CollectionSettingStorage newSettingStorage() {
		return new CollectionSettingStorage(
				directoryPath, settingSerializer, settingDeserializer);
	}
	
	public void mkdir() {
		Directories.mkdirs(directoryPath.toFile());
	}
	
	public void storeSetting(CollectionSetting setting) {
		settingStorage.store(setting);
	}
	
	public void storeRecord(
			Record record, 
			Map<String, FieldSetting> settings, EzyObject data) {
		recordStore.write(record, settings, data);
	}
	
	public CollectionSetting readSetting() {
		CollectionSetting setting = settingStorage.read();
		setting.setName(collectionName);
		return setting;
	}
	
}

class CollectionSettingStorage {
	
	protected final Path filePath;
	protected final SettingCollectionSerializer settingSerializer;
	protected final SettingCollectionDeserializer settingDeserializer;
	
	public CollectionSettingStorage(
			Path collectionDirectoryPath,
			SettingCollectionSerializer settingSerializer, 
			SettingCollectionDeserializer settingDeserializer) {
		this.settingSerializer = settingSerializer;
		this.settingDeserializer = settingDeserializer;
		this.filePath = Paths.get(collectionDirectoryPath.toString(), FILE_SETTINGS_DATA);
	}
	
	public void store(CollectionSetting setting) {
		byte[] bytes = this.settingSerializer.serialize(setting);
		FileWriters.write(filePath, bytes);
	}
	
	public CollectionSetting read() {
		byte[] bytes = FileReaders.readBytes(filePath);
		CollectionSetting setting = settingDeserializer.deserialize(bytes);
		return setting;
	}
	
}

class CollectionRecordStore {
	
	protected final FileProxy file;
	protected final Path filePath;
	protected final RecordWriter recordWriter;
	
	public CollectionRecordStore(Path collectionDirectoryPath) {
		this.filePath = Paths.get(collectionDirectoryPath.toString(), FILE_RECORDS_DATA);
		this.file = new FileRandomAccessProxy(filePath.toFile(), MODE_READ_WRITE);
		this.recordWriter = new RecordWriter(file);
	}
	
	public void write(
			Record record, 
			Map<String, FieldSetting> settings, EzyObject data) {
		recordWriter.write(record, settings, data);
	}
	
}
