package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SendPongPacket extends WritePacket {

    private DataClient client_;

    public SendPongPacket(DataClient client) {
        super();
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
