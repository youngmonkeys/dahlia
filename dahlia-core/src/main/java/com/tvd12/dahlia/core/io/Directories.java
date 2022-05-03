package com.tvd12.dahlia.core.io;

import java.io.File;

public final class Directories {

    private Directories() {}

    public static void mkdirs(File dir) {
        if (!dir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dir.mkdirs();
        }
    }
}
