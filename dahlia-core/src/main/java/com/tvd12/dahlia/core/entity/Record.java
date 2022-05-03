package com.tvd12.dahlia.core.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@SuppressWarnings("rawtypes")
public class Record {

    protected final Comparable id;
    protected final long position;
    @Setter
    protected boolean alive;

    public Record(Comparable id, long position) {
        this.id = id;
        this.alive = true;
        this.position = position;
    }
}
