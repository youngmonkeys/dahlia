package com.tvd12.dahlia.core.io;

import java.io.IOException;

public interface FileProxy {

	void seek(long position) throws IOException;
	
	void write(byte value) throws IOException;
	
	void write(boolean value) throws IOException;
	
	void write(double value) throws IOException;
	
	void write(float value) throws IOException;
	
	void write(int value) throws IOException;
	
	void write(long value) throws IOException;
	
	void write(short value) throws IOException;
	
	void write(byte[] bytes) throws IOException;
	
}
