package com.tvd12.dahlia.core.command;

import com.tvd12.ezyfox.entity.EzyObject;

import lombok.Getter;

@Getter
public class CommandUpdate implements Command {

	protected EzyObject query;
	protected EzyObject options;
	protected int collectionId;
	
	public CommandUpdate(int collectionId, EzyObject query, EzyObject options) {
		this.query = query;
		this.options = options;
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.INSERT;
	}
}
