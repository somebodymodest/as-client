package com.mmobite.as.network.client;

import com.mmobite.as.network.packet.WritePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class PacketEncoder extends MessageToByteEncoder<WritePacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, WritePacket pkt, ByteBuf out) {
        // pack data into embedded buffer
        pkt.writeC((byte) pkt.getOpcode());
        pkt.writeBody();

        int dataLength = pkt.getBuffer().readableBytes();

        // send data to outbound handler as new ByteBuf
        out.writeShortLE(dataLength + 2);
        out.writeBytes(pkt.getBuffer());
    }
}
