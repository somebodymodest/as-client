package com.mmobite.as.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Ivan on 22.03.2018.
 */
public abstract class WritePacket implements IWritePacket {

    protected ByteBuf buf_;

    public ByteBuf getBuffer() {
        return buf_;
    }

    public void setBuffer(ByteBuf buf) {
        buf_ = buf;
    }

    public void writeC(byte value) {
        buf_.writeByte(value);
    }

    public void writeH(int value) {
        buf_.writeShortLE((short) value);
    }

    public void writeD(int value) {
        buf_.writeIntLE(value);
    }

    public void writeQ(long value) {
        buf_.writeLongLE(value);
    }

    public void writeF(double value) {
        buf_.writeDoubleLE(value);
    }

    public void writeS(String value) {
        int length = value.length();
        for (int i = 0; i < length; i++) {
            buf_.writeShortLE(value.charAt(i));
        }
        buf_.writeShortLE('\000');
    }

    public void writeB(byte[] data) {
        buf_.writeBytes(data);
    }
}
