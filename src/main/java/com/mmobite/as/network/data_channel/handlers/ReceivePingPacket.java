package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.packets.OpcodeSC;
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
        //DataClient c = (DataClient)client;
        //log.debug("data_channel: ReceivePingPacket session[{}]", c.getGameSessionHandle());
        client.sendPacket(new SendPongPacket());
    }
}
