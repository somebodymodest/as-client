package com.mmobite.as.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Ivan on 22.03.2018.
 */
public abstract class WritePacket implements IWritePacket {

    protected short opcode_;
    protected ByteBuf buf_;
    protected ChannelHandlerContext ctx_;

    public ByteBuf getBuffer() {
        return buf_;
    }

    public void setBuffer(ByteBuf buf) {
        buf_ = buf;
    }

    public ChannelHandlerContext getChannel() {
        return ctx_;
    }

    public void setChannel(ChannelHandlerContext ctx) {
        ctx_ = ctx;
    }

    public void setOpcode(short opcode) {
        opcode_ = opcode;
    }

    public short getOpcode() {
        return opcode_;
    }

    protected void writeC(byte value) {
        buf_.writeByte(value);
    }

    protected void writeH(int value) {
        buf_.writeShortLE((short) value);
    }

    protected void writeD(int value) {
        buf_.writeIntLE(value);
    }

    protected void writeQ(long value) {
        buf_.writeLongLE(value);
    }

    protected void writeF(double value) {
        buf_.writeDoubleLE(value);
    }

    protected void writeS(String value) {
        int length = value.length();
        for (int i = 0; i < length; i++) {
            buf_.writeShortLE(value.charAt(i));
        }
        buf_.writeShortLE('\000');
    }

    protected void writeB(byte[] data) {
        buf_.writeBytes(data);
    }

}
