package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.query.FindOptions;
import com.tvd12.ezyfox.entity.EzyObject;

import lombok.Getter;

@Getter
public class Find implements Command {

	protected int collectionId;
	protected EzyObject query;
	protected FindOptions options;
	
	public Find(int collectionId, EzyObject query) {
		this(collectionId, query, new FindOptions());
	}
	
	public Find(int collectionId, EzyObject query, FindOptions options) {
		this.query = query;
		this.options = options;
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.FIND;
	}
	
}
