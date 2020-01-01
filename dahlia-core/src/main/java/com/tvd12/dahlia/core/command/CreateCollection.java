package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.setting.CollectionSetting;

import lombok.Getter;

@Getter
public class CreateCollection implements Command {

	protected final int databaseId;
	protected final CollectionSetting setting;
	
	public CreateCollection(int databaseId, CollectionSetting setting) {
		this.setting = setting;
		this.databaseId = databaseId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.CREATE_COLLECTION;
	}
	
}
