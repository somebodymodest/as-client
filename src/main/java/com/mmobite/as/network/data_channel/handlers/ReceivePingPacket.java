package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.packets.OpcodeSC;
import com.mmobite.as.network.packet.ReadPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivePingPacket extends ReadPacket {

    private static Logger log = LoggerFactory.getLogger(ReceivePingPacket.class.getName());

    @Override
    public int getOpcode() {
        return OpcodeSC.pingpacket;
    }

    @Override
    public boolean read() {
        // nothing to read
        return true;
    }

    @Override
    public void run(ITcpClient client) {
        //log.debug("ReceivePingPacket");
        client.sendPacket(new SendPongPacket());
    }
}
