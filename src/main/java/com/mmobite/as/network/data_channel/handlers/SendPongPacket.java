package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.packets.OpcodeCS;
import com.mmobite.as.network.packet.WritePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendPongPacket extends WritePacket {

    private static Logger log = LoggerFactory.getLogger(SendPongPacket.class.getName());

    public SendPongPacket() {
        super();
    }

    @Override
    public int getOpcode() {
        return OpcodeCS.pongpacket;
    }

    @Override
    public void writeBody() {
    }
}
