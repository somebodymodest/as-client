package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.ctrl_channel.packets.OpcodeCS;
import com.mmobite.as.network.packet.WritePacket;

public class SendDummyPacket extends WritePacket {

    @Override
    public int getOpcode() {
        return OpcodeCS.dummypacket;
    }

    @Override
    public void writeBody() {
    }

}
