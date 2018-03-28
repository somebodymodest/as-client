package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.ctrl_channel.client.CtrlClient;
import com.mmobite.as.network.ctrl_channel.packets.SC_Opcodes;
import com.mmobite.as.network.packet.ReadPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivePingPacket extends ReadPacket {

    private static Logger log = LoggerFactory.getLogger(ReceivePingPacket.class.getName());

    @Override
    public short getOpcode() {
        return SC_Opcodes.pingpacket;
    }

    @Override
    public boolean read() {
        // nothing to read
        return true;
    }

    @Override
    public void run(ITcpClient client) {
        log.debug("Receive ping");
        client.sendPacket(new SendPongPacket((CtrlClient) client));
    }

}
