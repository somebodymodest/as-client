package com.mmobite.as.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // Wait until the length prefix is available.
        if (in.readableBytes() < 2) {
            return;
        }

        // Wait until the whole data is available.
        in.markReaderIndex();
        int dataLength = (in.readShortLE() - 2);
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        // Send data to inbound handler as new ByteBuf
        out.add(in.readBytes(dataLength));
    }
}
