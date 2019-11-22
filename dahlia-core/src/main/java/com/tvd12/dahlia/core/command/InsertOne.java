package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.constant.CommandType;
import com.tvd12.ezyfox.entity.EzyObject;

import lombok.Getter;

@Getter
public class InsertOne implements Command {

	protected EzyObject data;
	protected int collectionId;
	
	@Override
	public CommandType getType() {
		return CommandType.INSERT_ONE;
	}
}
