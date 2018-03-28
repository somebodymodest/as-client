package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;
import io.netty.buffer.ByteBuf;

public class SendPongPacket extends WritePacket {

    private DataClient client_;

    public SendPongPacket(DataClient client) {
        client_ = client;
        setBuffer(client_.getChannel().alloc().buffer(256));
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.pongpacket;
    }

    @Override
    public void writeBody() {

    }
}
