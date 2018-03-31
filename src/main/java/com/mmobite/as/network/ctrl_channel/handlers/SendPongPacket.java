package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.ctrl_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;

public class SendPongPacket extends WritePacket {

    @Override
    public short getOpcode() {
        return CS_Opcodes.pongpacket;
    }

    @Override
    public void writeBody() {
    }
}
