package com.tvd12.dahlia.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.tvd12.dahlia.core.codec.SettingCollectionDeserializer;
import com.tvd12.dahlia.core.codec.SettingCollectionSerializer;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.io.Directories;
import com.tvd12.dahlia.core.io.FileReaders;
import com.tvd12.dahlia.core.io.FileWriters;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.ezyfox.codec.MsgPackBytesSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;

public class CollectionStorage {

	protected final String databaseName;
	protected final String collectionName;
	protected final String storageDirectory;
	protected final Path directoryPath;
	protected final Path settingFilePath;
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
		this.settingFilePath = Paths.get(directoryPath.toString(), 
				Constants.FILE_SETTINGS_DATA);
	}
	
	public void mkdir() {
		Directories.mkdirs(directoryPath.toFile());
	}
	
	public void storeSetting(CollectionSetting setting) {
		byte[] bytes = this.settingSerializer.serialize(setting);
		FileWriters.write(settingFilePath, bytes);
	}
	
	public CollectionSetting readSetting() {
		byte[] bytes = FileReaders.readBytes(settingFilePath);
		CollectionSetting setting = settingDeserializer.deserialize(bytes);
		setting.setName(collectionName);
		return setting;
	}
	
}
