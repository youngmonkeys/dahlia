package com.tvd12.dahlia.core.io;

import java.io.IOException;

public interface FileProxy {

    void seek(long position) throws IOException;

    boolean readBoolean() throws IOException;

    byte readByte() throws IOException;

    double readDouble() throws IOException;

    float readFloat() throws IOException;

    int readInt() throws IOException;

    long readLong() throws IOException;

    short readShort() throws IOException;

    byte[] readBytes(int size) throws IOException;

    String readString(int size) throws IOException;

    void writeByte(byte value) throws IOException;

    void writeBoolean(boolean value) throws IOException;

    void writeDouble(double value) throws IOException;

    void writeFloat(float value) throws IOException;

    void writeInt(int value) throws IOException;

    void writeLong(long value) throws IOException;

    void writeShort(short value) throws IOException;

    void writeBytes(byte[] bytes) throws IOException;

    long length() throws IOException;
}
