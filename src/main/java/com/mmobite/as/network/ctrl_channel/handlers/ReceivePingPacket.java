package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.ctrl_channel.packets.OpcodeSC;
import com.mmobite.as.network.packet.ReadPacket;

public class ReceivePingPacket extends ReadPacket {

    //private final static Logger log = LoggerFactory.getLogger(ReceivePingPacket.class.getName());

    @Override
    public int getOpcode() {
        return OpcodeSC.pingpacket;
    }

    @Override
    public boolean read() {
        return true;
    }

    @Override
    public void run(ITcpClient client) {
        //log.debug("ctrl_channel: ReceivePingPacket");
        client.sendPacket(new SendPongPacket());
    }
}
