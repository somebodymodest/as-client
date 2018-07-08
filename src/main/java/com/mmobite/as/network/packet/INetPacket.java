package com.mmobite.as.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by Ivan on 23.03.2018.
 */
public interface INetPacket {
    int getOpcode();
    ByteBuf getBuffer();
    void setBuffer(ByteBuf buf);
    void releaseBuffer();
}
