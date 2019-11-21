package com.tvd12.dahlia.core.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileRandomAccessProxy implements FileProxy {

	protected final RandomAccessFile fileAccess;
	
	public FileRandomAccessProxy(File file, String mode) {
		this.fileAccess = FileCreators.newRandomAccessFile(file, mode);
	}
	
	@Override
	public void seek(long position) throws IOException {
		fileAccess.seek(position);
	}
	
	@Override
	public void write(byte value) throws IOException {
		fileAccess.write(value);
	}

	@Override
	public void write(boolean value) throws IOException {
		fileAccess.writeBoolean(value);
	}

	@Override
	public void write(double value) throws IOException {
		fileAccess.writeDouble(value);
	}

	@Override
	public void write(float value) throws IOException {
		fileAccess.writeFloat(value);
	}

	@Override
	public void write(int value) throws IOException {
		fileAccess.write(value);
	}

	@Override
	public void write(long value) throws IOException {
		fileAccess.writeLong(value);
	}

	@Override
	public void write(short value) throws IOException {
		fileAccess.writeShort(value);
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		fileAccess.write(bytes);
	}

}
