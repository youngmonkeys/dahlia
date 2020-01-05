package com.tvd12.dahlia.core.command;

import com.tvd12.ezyfox.entity.EzyObject;

import lombok.Getter;

@Getter
public class CommandFind implements Command {

	protected int collectionId;
	protected EzyObject query;
	protected EzyObject options;
	
	public CommandFind(int collectionId, EzyObject query, EzyObject options) {
		this.query = query;
		this.options = options;
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.FIND;
	}
	
}
