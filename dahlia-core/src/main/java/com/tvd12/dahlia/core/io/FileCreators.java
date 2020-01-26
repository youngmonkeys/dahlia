package com.tvd12.dahlia.core.io;

import java.io.File;
import java.io.RandomAccessFile;

final class FileCreators {

	private FileCreators() {}
	
	public static RandomAccessFile newRandomAccessFile(File file, String mode) {
		try {
			if(!file.exists())
				file.createNewFile();
			return new RandomAccessFile(file, mode);
		} catch (Exception e) {
			throw new IllegalArgumentException("can create random access file: " + file + " with mode: " + mode, e);
		}
	}
	
}
