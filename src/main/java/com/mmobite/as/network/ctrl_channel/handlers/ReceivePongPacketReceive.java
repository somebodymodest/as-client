package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.packet.ReceiveDummyPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivePongPacketReceive extends ReceiveDummyPacket {

    private static Logger log = LoggerFactory.getLogger(ReceivePongPacketReceive.class.getName());

    @Override
    public boolean read() {
        // nothing to read
        return true;
    }

    @Override
    public void run() {
        log.debug("Receive pong");
        // do nothing
    }

}
