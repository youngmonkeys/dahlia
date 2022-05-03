package com.tvd12.dahlia.core.command;

import com.tvd12.ezyfox.entity.EzyObject;
import lombok.Getter;

@Getter
public class CommandInsertOne implements Command {

    protected EzyObject data;
    protected int collectionId;

    public CommandInsertOne(int collectionId, EzyObject data) {
        this.data = data;
        this.collectionId = collectionId;
    }

    @Override
    public CommandType getType() {
        return CommandType.INSERT_ONE;
    }
}
