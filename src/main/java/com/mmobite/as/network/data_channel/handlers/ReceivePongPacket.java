package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.packets.SC_Opcodes;
import com.mmobite.as.network.packet.ReadPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivePongPacket extends ReadPacket {

    private static Logger log = LoggerFactory.getLogger(ReceivePongPacket.class.getName());

    @Override
    public short getOpcode() {
        return SC_Opcodes.pongpacket;
    }

    @Override
    public boolean read() {
        // nothing to read
        return true;
    }

    @Override
    public void run(ITcpClient client) {
        log.debug("ReceivePongPacket");
        // do nothing
    }
}
