package com.tvd12.dahlia.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public final class FileCreators {

	private FileCreators() {}
	
	public static RandomAccessFile newRandomAccessFile(File file, String mode) {
		try {
			return new RandomAccessFile(file, mode);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("can create random access file: " + file + " with mode: " + mode);
		}
	}
	
}
