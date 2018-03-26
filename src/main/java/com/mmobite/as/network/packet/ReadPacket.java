package com.mmobite.as.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Ivan on 22.03.2018.
 */
public abstract class ReadPacket implements IReadPacket {

    protected short opcode_;
    protected ByteBuf buf_;
    protected ChannelHandlerContext ctx_;

    public ReadPacket() {
    }

    public ReadPacket(ByteBuf buf) {
        setBuffer(buf);
    }

    public ReadPacket(ByteBuf buf, ChannelHandlerContext ctx) {
        setBuffer(buf);
        setChannel(ctx);
    }

    public ReadPacket(byte[] bytes, ChannelHandlerContext ctx) {
        buf_.readBytes(bytes);
        setChannel(ctx);
    }

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

    protected int getAvailableBytes() {
        return buf_.readableBytes();
    }

    protected int readC() {
        return buf_.readByte();
    }

    protected int readH() {
        return buf_.readShortLE();
    }

    protected int readD() {
        return buf_.readIntLE();
    }

    protected long readQ() {
        return buf_.readLongLE();
    }

    protected double readF() {
        return buf_.readDoubleLE();
    }

    protected String readS() {
        StringBuilder sb = new StringBuilder();
        char ch;
        while ((ch = (char) buf_.readShortLE()) != 0)
            sb.append(ch);
        return sb.toString();
    }

    protected void readB(byte[] dst) {
        buf_.readBytes(dst);
    }

    protected void readB(byte[] dst, int offset, int len) {
        buf_.readBytes(dst, offset, len);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
