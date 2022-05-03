package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileReaders {

    private FileReaders() {}

    public static byte[] readBytes(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("can't read file: " + path, e);
        }
    }
}
