package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.OpcodeSC;
import com.mmobite.as.network.packet.ReadPacket;

public class ReceiveStartTracePacket extends ReadPacket {

    @Override
    public int getOpcode() {
        return OpcodeSC.starttracepacket;
    }

    @Override
    public boolean read() {
        return true;
    }

    @Override
    public void run(ITcpClient client) {
        DataClient c = (DataClient)client;
        c.setBlocked(false);
    }
}
