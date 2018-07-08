package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.ctrl_channel.packets.OpcodeCS;
import com.mmobite.as.network.packet.WritePacket;

public class SendPongPacket extends WritePacket {

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
