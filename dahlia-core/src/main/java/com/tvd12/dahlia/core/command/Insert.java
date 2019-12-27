package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.constant.CommandType;
import com.tvd12.ezyfox.entity.EzyArray;

import lombok.Getter;

@Getter
public class Insert implements Command {

	protected EzyArray data;
	protected int collectionId;
	
	public Insert(int collectionId, EzyArray data) {
		this.data = data;
		this.collectionId = collectionId;
	}
	
	@Override
	public CommandType getType() {
		return CommandType.INSERT;
	}
}
