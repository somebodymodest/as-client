package com.mmobite.as.network.packet;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ivan on 23.03.2018.
 */
public class ReceiveDummyPacket extends ReadPacket {

    private static Logger log = LoggerFactory.getLogger(ReceiveDummyPacket.class.getName());

    public ReceiveDummyPacket() {
        super();
    }

    public ReceiveDummyPacket(byte[] bytes, ChannelHandlerContext ctx) {
        super(bytes, ctx);
    }

    @Override
    public boolean read() {
        return true;
    }

    @Override
    public void run() {
        log.error("Called ReceiveDummyPacket opcode=[{}]", getOpcode());
    }

}
