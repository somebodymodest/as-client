package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.packets.OpcodeCS;
import com.mmobite.as.network.packet.WritePacket;

public class SendHwidPacket extends WritePacket {

    private String hwid_;

    public SendHwidPacket(String hwid) {
        super();
        hwid_ = hwid;
    }

    @Override
    public void writeBody() {
        writes(hwid_);
    }

    @Override
    public int getOpcode() {
        return OpcodeCS.hwidpacket;
    }
}
