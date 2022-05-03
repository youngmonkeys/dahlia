package com.tvd12.dahlia.core.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileRandomAccess implements FileProxy {

    protected final RandomAccessFile fileAccess;

    public FileRandomAccess(File file, String mode) {
        this.fileAccess = FileCreators.newRandomAccessFile(file, mode);
    }

    @Override
    public void seek(long position) throws IOException {
        fileAccess.seek(position);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return fileAccess.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return fileAccess.readByte();
    }

    @Override
    public double readDouble() throws IOException {
        return fileAccess.readDouble();
    }

    @Override
    public float readFloat() throws IOException {
        return fileAccess.readFloat();
    }

    @Override
    public int readInt() throws IOException {
        return fileAccess.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return fileAccess.readLong();
    }

    @Override
    public short readShort() throws IOException {
        return fileAccess.readShort();
    }

    @Override
    public byte[] readBytes(int size) throws IOException {
        byte[] bytes = new byte[size];
        fileAccess.read(bytes);
        return bytes;
    }

    @Override
    public String readString(int size) throws IOException {
        byte[] bytes = readBytes(size);
        return new String(bytes);
    }

    @Override
    public void writeByte(byte value) throws IOException {
        fileAccess.write(value);
    }

    @Override
    public void writeBoolean(boolean value) throws IOException {
        fileAccess.writeBoolean(value);
    }

    @Override
    public void writeDouble(double value) throws IOException {
        fileAccess.writeDouble(value);
    }

    @Override
    public void writeFloat(float value) throws IOException {
        fileAccess.writeFloat(value);
    }

    @Override
    public void writeInt(int value) throws IOException {
        fileAccess.writeInt(value);
    }

    @Override
    public void writeLong(long value) throws IOException {
        fileAccess.writeLong(value);
    }

    @Override
    public void writeShort(short value) throws IOException {
        fileAccess.writeShort(value);
    }

    @Override
    public void writeBytes(byte[] bytes) throws IOException {
        fileAccess.write(bytes);
    }

    @Override
    public long length() throws IOException {
        return fileAccess.length();
    }
}
