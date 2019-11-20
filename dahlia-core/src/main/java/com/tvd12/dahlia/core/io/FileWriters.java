package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileWriters {

	private FileWriters() {}
	
	public static void write(Path path, byte[] bytes) {
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			throw new IllegalArgumentException("can't write: " + bytes.length + " bytes to: " + path);
		}
	}
	
}
