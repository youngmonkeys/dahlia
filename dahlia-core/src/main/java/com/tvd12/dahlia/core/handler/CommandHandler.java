package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.Command;

public interface CommandHandler<C extends Command> {

    Object handle(C command);
}
