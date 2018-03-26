package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.ReceiveDummyPacket;
import com.mmobite.as.network.packet.SendDummyPacket;

public class SendPongPacket extends SendDummyPacket {
    SendPongPacket(ReceiveDummyPacket request) {
        super(request);

        setOpcode(CS_Opcodes.pongpacket);
    }
}
