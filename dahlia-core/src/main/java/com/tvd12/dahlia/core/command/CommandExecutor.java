package com.tvd12.dahlia.core.command;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.constant.CommandType;
import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.entity.DatabasesAware;
import com.tvd12.dahlia.core.factory.CollectionFactory;
import com.tvd12.dahlia.core.factory.CollectionFactoryAware;
import com.tvd12.dahlia.core.factory.CollectionStorageFactory;
import com.tvd12.dahlia.core.factory.CollectionStorageFactoryAware;
import com.tvd12.dahlia.core.factory.DatabaseFactory;
import com.tvd12.dahlia.core.factory.DatabaseFactoryAware;
import com.tvd12.dahlia.core.factory.DatabaseStorageFactory;
import com.tvd12.dahlia.core.factory.DatabaseStorageFactoryAware;
import com.tvd12.dahlia.core.handler.CommandCreateCollectionHandler;
import com.tvd12.dahlia.core.handler.CommandCreateDatabaseHandler;
import com.tvd12.dahlia.core.handler.CommandHandler;
import com.tvd12.dahlia.core.handler.CommandInsertOneHandler;
import com.tvd12.dahlia.core.setting.RecordSizeReader;
import com.tvd12.dahlia.core.setting.RecordSizeReaderAware;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.dahlia.core.setting.RuntimeSettingAware;
import com.tvd12.dahlia.core.storage.Storage;
import com.tvd12.dahlia.core.storage.StorageAware;
import com.tvd12.ezyfox.builder.EzyBuilder;

public class CommandExecutor {

	protected final Storage storage;
	protected final Databases databases;
	protected final RuntimeSetting runtimeSetting;
	protected final RecordSizeReader recordSizeReader;
	protected final DatabaseFactory databaseFactory;
	protected final CollectionFactory collectionFactory;
	protected final DatabaseStorageFactory databaseStorageFactory;
	protected final CollectionStorageFactory collectionStorageFactory;
	protected final Map<CommandType, CommandHandler> handlers;
	
	protected CommandExecutor(Builder builder) {
		this.storage = builder.storage;
		this.databases = builder.databases;
		this.runtimeSetting = builder.runtimeSetting;
		this.recordSizeReader = builder.recordSizeReader;
		this.databaseFactory = builder.databaseFactory;
		this.collectionFactory = builder.collectionFactory; 
		this.databaseStorageFactory = builder.databaseStorageFactory;
		this.collectionStorageFactory = builder.collectionStorageFactory;
		this.handlers = newHandlers();
	}
	
	public <T> T execute(Command command) {
		CommandType type = command.getType();
		CommandHandler handler = handlers.get(type);
		T answer = (T) handler.handle(command);
		return answer;
	}
	
	protected Map<CommandType, CommandHandler> newHandlers() {
		Map<CommandType, CommandHandler> map = new HashMap<>();
		addHandlers(map, CommandType.INSERT_ONE, new CommandInsertOneHandler());
		addHandlers(map, CommandType.CREATE_DATABASE, new CommandCreateDatabaseHandler());
		addHandlers(map, CommandType.CREATE_COLLECTION, new CommandCreateCollectionHandler());
		return map;
	}
	
	protected void addHandlers(
			Map<CommandType, CommandHandler> map, 
			CommandType type,
			CommandHandler handler) {
		if(handler instanceof StorageAware)
			((StorageAware)handler).setStorage(storage);
		if(handler instanceof DatabasesAware)
			((DatabasesAware)handler).setDatabases(databases);
		if(handler instanceof RuntimeSettingAware)
			((RuntimeSettingAware)handler).setRuntimeSetting(runtimeSetting);
		if(handler instanceof RecordSizeReaderAware)
			((RecordSizeReaderAware)handler).setRecordSizeReader(recordSizeReader);
		if(handler instanceof DatabaseFactoryAware)
			((DatabaseFactoryAware)handler).setDatabaseFactory(databaseFactory);
		if(handler instanceof CollectionFactoryAware)
			((CollectionFactoryAware)handler).setCollectionFactory(collectionFactory);
		if(handler instanceof DatabaseStorageFactoryAware)
			((DatabaseStorageFactoryAware)handler).setDatabaseStorageFactory(databaseStorageFactory);
		if(handler instanceof CollectionStorageFactoryAware)
			((CollectionStorageFactoryAware)handler).setCollectionStorageFactory(collectionStorageFactory);
		map.put(type, handler);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<CommandExecutor> {
		
		protected Storage storage;
		protected Databases databases;
		protected RuntimeSetting runtimeSetting;
		protected RecordSizeReader recordSizeReader;
		protected DatabaseFactory databaseFactory;
		protected CollectionFactory collectionFactory;
		protected DatabaseStorageFactory databaseStorageFactory;
		protected CollectionStorageFactory collectionStorageFactory;
		
		public Builder storage(Storage storage) {
			this.storage = storage;
			return this;
		}
		
		public Builder databases(Databases databases) {
			this.databases = databases;
			return this;
		}
		
		public Builder runtimeSetting(RuntimeSetting runtimeSetting) {
			this.runtimeSetting = runtimeSetting;
			return this;
		}
		
		public Builder recordSizeReader(RecordSizeReader recordSizeReader) {
			this.recordSizeReader = recordSizeReader;
			return this;
		}
		
		public Builder databaseFatory(DatabaseFactory databaseFactory) {
			this.databaseFactory = databaseFactory;
			return this;
		}
		
		public Builder collectionFactory(CollectionFactory collectionFactory) {
			this.collectionFactory = collectionFactory;
			return this;
		}
		
		public Builder databaseStorageFactory(DatabaseStorageFactory databaseStorageFactory) {
			this.databaseStorageFactory = databaseStorageFactory;
			return this;
		}
		
		public Builder collectionStorageFactory(CollectionStorageFactory collectionStorageFactory) {
			this.collectionStorageFactory = collectionStorageFactory;
			return this;
		}
		
		@Override
		public CommandExecutor build() {
			return new CommandExecutor(this);
		}
		
	}
	
}
