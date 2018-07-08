package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.ctrl_channel.packets.OpcodeSC;
import com.mmobite.as.network.packet.ReadPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivePongPacket extends ReadPacket {

    @Override
    public int getOpcode() {
        return OpcodeSC.pongpacket;
    }

    @Override
    public boolean read() {
        return true;
    }

    @Override
    public void run(ITcpClient client) {
    }
}
