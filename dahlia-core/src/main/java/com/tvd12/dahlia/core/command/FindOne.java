package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.constant.CommandType;
import com.tvd12.ezyfox.entity.EzyObject;

import lombok.Getter;

@Getter
public class FindOne implements Command {

	protected EzyObject query;
	protected int collectionId;
	
	public FindOne(int collectionId, EzyObject query) {
		this.query = query;
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.FIND_ONE;
	}
	
}
