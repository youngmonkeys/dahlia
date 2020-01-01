package com.tvd12.dahlia.core.command;

import lombok.Getter;

@Getter
public class Count implements Command {

	protected int collectionId;
	
	public Count(int collectionId) {
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.COUNT;
	}
	
}
