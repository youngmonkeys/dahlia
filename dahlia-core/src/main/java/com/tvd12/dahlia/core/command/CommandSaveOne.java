package com.tvd12.dahlia.core.command;

import com.tvd12.ezyfox.entity.EzyObject;
import lombok.Getter;

@Getter
public class CommandSaveOne implements Command {

    protected EzyObject data;
    protected int collectionId;

    public CommandSaveOne(int collectionId, EzyObject data) {
        this.data = data;
        this.collectionId = collectionId;
    }

    @Override
    public CommandType getType() {
        return CommandType.SAVE_ONE;
    }
}
