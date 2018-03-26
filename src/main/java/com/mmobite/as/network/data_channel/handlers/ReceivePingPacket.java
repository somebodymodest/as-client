package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.packet.ReceiveDummyPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivePingPacket extends ReceiveDummyPacket {

    private static Logger log = LoggerFactory.getLogger(ReceivePingPacket.class.getName());

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
