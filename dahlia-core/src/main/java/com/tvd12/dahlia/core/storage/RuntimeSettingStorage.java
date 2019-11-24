package com.tvd12.dahlia.core.storage;

import static com.tvd12.dahlia.core.constant.Constants.FILE_RUNTIME_DATA;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.tvd12.dahlia.core.codec.SettingRuntimeDeserializer;
import com.tvd12.dahlia.core.codec.SettingRuntimeSerializer;
import com.tvd12.dahlia.core.io.FileReaders;
import com.tvd12.dahlia.core.io.FileWriters;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;

public class RuntimeSettingStorage {
	
	protected final Path filePath;
	protected final SettingRuntimeSerializer settingSerializer;
	protected final SettingRuntimeDeserializer settingDeserializer;
	
	protected RuntimeSettingStorage(Builder builder) {
		this.settingSerializer = new SettingRuntimeSerializer(builder.objectSerializer);
		this.settingDeserializer = new SettingRuntimeDeserializer(builder.objectDeserializer);
		this.filePath = Paths.get(builder.storageDirectory, FILE_RUNTIME_DATA);
	}
	
	public void store(RuntimeSetting setting) {
		byte[] bytes = this.settingSerializer.serialize(setting);
		FileWriters.write(filePath, bytes);
	}
	
	public RuntimeSetting read() {
		RuntimeSetting setting = null;
		if(Files.exists(filePath)) {
			byte[] bytes = FileReaders.readBytes(filePath);
			setting = settingDeserializer.deserialize(bytes);
		}
		else {
			setting = new RuntimeSetting();
		}
		return setting;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<RuntimeSettingStorage> {
		
		protected String storageDirectory;
		protected EzyObjectSerializer objectSerializer;
		protected EzyObjectDeserializer objectDeserializer;
		
		public Builder storageDirectory(String storageDirectory) {
			this.storageDirectory = storageDirectory;
			return this;
		}
		
		public Builder objectSerializer(EzyObjectSerializer objectSerializer) {
			this.objectSerializer = objectSerializer;
			return this;
		}
		
		public Builder objectDeserializer(EzyObjectDeserializer objectDeserializer) {
			this.objectDeserializer = objectDeserializer;
			return this;
		}
		
		@Override
		public RuntimeSettingStorage build() {
			return new RuntimeSettingStorage(this);
		}
		
	}
	
}