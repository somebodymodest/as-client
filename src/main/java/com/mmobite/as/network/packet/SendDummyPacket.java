package com.mmobite.as.network.packet;

import com.mmobite.as.network.ctrl_channel.packets.CS_Opcodes;
import io.netty.channel.ChannelHandlerContext;

public class SendDummyPacket extends WritePacket {

    public SendDummyPacket(ChannelHandlerContext ctx) {
        setOpcode(CS_Opcodes.dummypacket);
        setBuffer(ctx.alloc().buffer());
        setChannel(ctx);
    }

    public SendDummyPacket(ReceiveDummyPacket request) {
        setOpcode(CS_Opcodes.dummypacket);
        setBuffer(request.getBuffer());
        setChannel(request.getChannel());
        buf_.retain();
    }

    @Override
    public void writeBody() {
    }

    public void writeHeader() {
        // calc & write packet size
        short dataLength = (short) (buf_.readableBytes());
        buf_.markWriterIndex();
        buf_.writerIndex(buf_.writerIndex() - dataLength);
        buf_.writeShortLE(dataLength);
        buf_.resetWriterIndex();
    }

    @Override
    public void write() {
        writeH(0);// write packet size (unknown yet)
        writeC((byte) opcode_);
        writeBody();
        writeHeader();
    }

    public void sendPacket()
    {
        write();
        ctx_.writeAndFlush(getBuffer());
    }
}
