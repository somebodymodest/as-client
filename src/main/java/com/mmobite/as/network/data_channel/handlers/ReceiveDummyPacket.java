package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.packets.OpcodeCS;
import com.mmobite.as.network.packet.ReadPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ivan on 23.03.2018.
 */
public class ReceiveDummyPacket extends ReadPacket {

    private static Logger log = LoggerFactory.getLogger(ReceiveDummyPacket.class.getName());

    @Override
    public int getOpcode() {
        return OpcodeCS.dummypacket;
    }

    @Override
    public boolean read() {
        return true;
    }

    @Override
    public void run(ITcpClient client) {
        log.error("Called ReceiveDummyPacket opcode=[{}]", getOpcode());
    }

}
