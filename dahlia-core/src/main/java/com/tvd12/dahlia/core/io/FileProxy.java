package com.tvd12.dahlia.core.io;

import java.io.IOException;

public interface FileProxy {

	void seek(long position) throws IOException;
	
	byte readyByte() throws IOException;
	
	long readLong() throws IOException;
	
	byte[] readBytes(int size) throws IOException;
	
	String readString(int size) throws IOException;
	
	void writeByte(byte value) throws IOException;
	
	void writeBool(boolean value) throws IOException;
	
	void writeDouble(double value) throws IOException;
	
	void writeFloat(float value) throws IOException;
	
	void writeInt(int value) throws IOException;
	
	void writeLong(long value) throws IOException;
	
	void writeShort(short value) throws IOException;
	
	void writeBytes(byte[] bytes) throws IOException;
	
	long length() throws IOException;
	
}
