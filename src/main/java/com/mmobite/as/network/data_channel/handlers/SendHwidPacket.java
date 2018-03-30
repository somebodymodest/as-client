package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;
import io.netty.buffer.Unpooled;

public class SendHwidPacket extends WritePacket {

    private DataClient client_;
    private String hwid_;

    public SendHwidPacket(DataClient client, String hwid) {
        super();
        client_ = client;
        hwid_ = hwid;
    }

    @Override
    public void writeBody() {
        writes(hwid_);
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.hwidpacket;
    }
}
