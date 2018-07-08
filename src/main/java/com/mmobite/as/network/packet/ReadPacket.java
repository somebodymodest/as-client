package com.mmobite.as.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Ivan on 22.03.2018.
 */
public abstract class ReadPacket implements IReadPacket {

    public ByteBuf buf_;

    public ByteBuf getBuffer() {
        return buf_;
    }

    public void setBuffer(ByteBuf buf) {
        buf_ = buf;
    }

    public void releaseBuffer() {
        if (buf_ != null)
            buf_.release();
    }

    public void setBuffer(byte[] bytes) {
        buf_.readBytes(bytes);
    }

    public int getAvailableBytes() {
        return buf_.readableBytes();
    }

    public byte readC() {
        return buf_.readByte();
    }

    public short readH() {
        return buf_.readShortLE();
    }

    public int readD() {
        return buf_.readIntLE();
    }

    public long readQ() {
        return buf_.readLongLE();
    }

    public double readF() {
        return buf_.readDoubleLE();
    }

    public String readS() {
        StringBuilder sb = new StringBuilder();
        char ch;
        while ((ch = (char) buf_.readShortLE()) != 0)
            sb.append(ch);
        return sb.toString();
    }

    public void readB(byte[] dst) {
        buf_.readBytes(dst);
    }

    public void readB(byte[] dst, int offset, int len) {
        buf_.readBytes(dst, offset, len);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
