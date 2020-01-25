package com.tvd12.dahlia.local;

import static com.tvd12.dahlia.core.constant.Constants.PREFIX_CLASSPATH;
import static com.tvd12.dahlia.core.constant.Constants.PREFIX_FILE;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.ICollection;
import com.tvd12.dahlia.IDatabase;
import com.tvd12.dahlia.core.command.CommandCreateCollection;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.exception.CollectionNotFoundException;
import com.tvd12.dahlia.local.setting.LocalCollectionSettingReader;

public class LocalDatabase implements IDatabase {

	protected final Database store;
	protected final CommandExecutor commandExecutor;
	protected final Map<String, ICollection> collections;
	protected final LocalCollectionSettingReader settingReader;
	
	public LocalDatabase(
			Database store, CommandExecutor commandExecutor) {
		this.store = store;
		this.commandExecutor = commandExecutor;
		this.collections = new ConcurrentHashMap<>();
		this.settingReader = new LocalCollectionSettingReader();
	}
	
	@Override
	public int getId() {
		return store.getId();
	}
	
	@Override
	public String getName() {
		return store.getName();
	}
	
	@Override
	public ICollection getCollection(String name) {
		return collections.computeIfAbsent(name, k -> {
			Collection collectionStore = store.getCollection(name);
			if(collectionStore == null)
				throw new CollectionNotFoundException(name);
			return new LocalCollection(collectionStore, commandExecutor);
		});
	}
	
	@Override
	public ICollection createCollection(Object setting) {
		CollectionSetting s = null;
		if(setting instanceof CollectionSetting) {
			s = (CollectionSetting)setting;
		}
		else if(setting instanceof File) {
			s = settingReader.readFileSetting((File) setting);
		}
		else if(setting instanceof InputStream) {
			s = settingReader.readInputStreamSetting((InputStream) setting);
		}
		else {
			String ss = (String)setting;
			if(ss.startsWith(PREFIX_FILE))
				s = settingReader.readFileSetting(ss.substring(PREFIX_FILE.length()));
			else if(ss.startsWith(PREFIX_CLASSPATH))
				s = settingReader.readFileSetting(ss.substring(PREFIX_CLASSPATH.length()));
			else
				s = settingReader.readJsonSetting(ss);
		}
		CommandCreateCollection command = new CommandCreateCollection(store.getId(), s);
		Collection collectionStore = commandExecutor.execute(command);
		LocalCollection collection = new LocalCollection(collectionStore, commandExecutor);
		collections.put(collectionStore.getName(), collection);
		return collection;
	}
	
}
