package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.ctrl_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;
import io.netty.buffer.Unpooled;

public class SendDummyPacket extends WritePacket {

    SendDummyPacket(){
        setBuffer(Unpooled.buffer(default_buffer_size_));
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.dummypacket;
    }

    @Override
    public void writeBody() {
    }

}
