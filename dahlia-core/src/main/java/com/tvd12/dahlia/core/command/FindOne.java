package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.constant.CommandType;

import lombok.Getter;

@Getter
public class FindOne implements Command {

	protected long id;
	protected int collectionId;
	
	public FindOne(int collectionId, long id) {
		this.id = id;
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.FIND_ONE;
	}
	
}
