package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.constant.CommandType;
import com.tvd12.ezyfox.entity.EzyObject;

import lombok.Getter;

@Getter
public class Find implements Command {

	protected int collectionId;
	protected EzyObject query;
	
	public Find(int collectionId, EzyObject query) {
		this.query = query;
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.FIND;
	}
	
}
