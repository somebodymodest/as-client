package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.ctrl_channel.client.CtrlClient;
import com.mmobite.as.network.ctrl_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SendPongPacket extends WritePacket {

    private CtrlClient client_;

    public SendPongPacket(CtrlClient client) {
        setBuffer(Unpooled.buffer(256));
        client_ = client;
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.pongpacket;
    }

    @Override
    public void writeBody() {
    }
}
