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

        in.markReaderIndex();

        // Wait until the whole data is available.
        short dataLength = (short) (in.readShortLE() - 2);
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }

        out.add(in.readBytes(dataLength));
    }
}
