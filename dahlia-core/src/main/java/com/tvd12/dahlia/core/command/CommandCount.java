package com.tvd12.dahlia.core.command;

import lombok.Getter;

@Getter
public class CommandCount implements Command {

	protected int collectionId;
	
	public CommandCount(int collectionId) {
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.COUNT;
	}
	
}
