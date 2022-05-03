package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.Command;
import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.entity.DatabasesAware;
import com.tvd12.dahlia.core.storage.Storage;
import com.tvd12.dahlia.core.storage.StorageAware;
import lombok.Setter;

@Setter
public abstract class CommandAbstractHandler<C extends Command>
    implements
    CommandHandler<C>,
    StorageAware,
    DatabasesAware {

    protected Storage storage;
    protected Databases databases;
}
