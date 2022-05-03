package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.Command;
import com.tvd12.dahlia.core.query.QueryToPredicate;
import com.tvd12.dahlia.core.query.QueryToPredicateAware;
import lombok.Setter;

@Setter
public abstract class CommandQueryHandler<C extends Command>
    extends CommandAbstractHandler<C>
    implements QueryToPredicateAware {

    protected QueryToPredicate queryToPredicate;
}
