package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.packet.ReceiveDummyPacket;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivePingPacketReceive extends ReceiveDummyPacket {

    private static Logger log = LoggerFactory.getLogger(ReceivePingPacketReceive.class.getName());

    @Override
    public boolean read() {
        // nothing to read
        return true;
    }

    @Override
    public void run() {
        log.debug("Receive ping");
        new SendPongPacket(this).sendPacket();
    }

}
