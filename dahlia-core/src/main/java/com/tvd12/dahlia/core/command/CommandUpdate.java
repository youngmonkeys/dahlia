package com.tvd12.dahlia.core.command;

import com.tvd12.ezyfox.entity.EzyObject;
import lombok.Getter;

@Getter
public class CommandUpdate implements Command {

    protected EzyObject query;
    protected EzyObject update;
    protected int collectionId;

    public CommandUpdate(int collectionId, EzyObject query, EzyObject update) {
        this.query = query;
        this.update = update;
        this.collectionId = collectionId;
    }

    @Override
    public CommandType getType() {
        return CommandType.UPDATE;
    }
}
