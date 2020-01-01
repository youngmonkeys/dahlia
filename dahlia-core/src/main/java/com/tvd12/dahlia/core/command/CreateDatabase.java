package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.setting.DatabaseSetting;

import lombok.Getter;

@Getter
public class CreateDatabase implements Command {

	protected final DatabaseSetting setting;
	
	public CreateDatabase(DatabaseSetting setting) {
		this.setting = setting;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.CREATE_DATABASE;
	}
	
}
