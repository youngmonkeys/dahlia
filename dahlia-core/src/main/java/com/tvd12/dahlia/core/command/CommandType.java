package com.tvd12.dahlia.core.command;

import lombok.Getter;

public enum CommandType {

    CREATE_DATABASE(1),
    CREATE_COLLECTION(2),
    INSERT(10),
    INSERT_ONE(11),
    SAVE(12),
    SAVE_ONE(13),
    FIND(20),
    FIND_ONE(21),
    UPDATE(30),
    DELETE(40),
    COUNT(50);

    @Getter
    private final int id;

    private CommandType(int id) {
        this.id = id;
    }

}
