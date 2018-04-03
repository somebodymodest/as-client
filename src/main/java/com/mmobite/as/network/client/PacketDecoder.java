package com.mmobite.as.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private static Logger log = LoggerFactory.getLogger(PacketDecoder.class.getName());

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        //log.debug("readableBytes() = {}", in.readableBytes());

        // Wait until the length prefix is available.
        if (in.readableBytes() < 2) {
            return;
        }

        in.markReaderIndex();

        // Wait until the whole data is available.
        int dataLength = (in.readShortLE() - 2);

        //log.debug("dataLength = {}", dataLength);

        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        //log.debug("got packet!");

        out.add(in.readBytes(dataLength));
    }
}
