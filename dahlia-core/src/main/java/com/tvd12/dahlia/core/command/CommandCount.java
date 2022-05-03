package com.tvd12.dahlia.core.command;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import lombok.Getter;

@Getter
public class CommandCount implements Command {

    protected EzyObject query;
    protected int collectionId;

    public CommandCount(int collectionId) {
        this(collectionId, EzyEntityFactory.EMPTY_OBJECT);
    }

    public CommandCount(int collectionId, EzyObject query) {
        this.query = query;
        this.collectionId = collectionId;
    }

    @Override
    public CommandType getType() {
        return CommandType.COUNT;
    }

}
