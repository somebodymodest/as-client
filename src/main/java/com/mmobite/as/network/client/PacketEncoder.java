package com.mmobite.as.network.client;

import com.mmobite.as.network.packet.WritePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class PacketEncoder extends MessageToByteEncoder<WritePacket> {

    private static Logger log = LoggerFactory.getLogger(PacketEncoder.class.getName());

    @Override
    protected void encode(ChannelHandlerContext ctx, WritePacket pkt, ByteBuf out) throws Exception {

        log.debug("enter encode: opcode[{}]", pkt.getOpcode());

        pkt.writeC((byte) pkt.getOpcode());
        pkt.writeBody();

        short dataLength = (short) (pkt.getBuffer().readableBytes());

        log.debug("dataLength: {}", dataLength);

        out.writeShortLE(dataLength + 2);
        out.writeBytes(pkt.getBuffer());
    }
}
