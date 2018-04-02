package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.SC_Opcodes;
import com.mmobite.as.network.packet.ReadPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiveStopTracePpacket extends ReadPacket {

    private static Logger log = LoggerFactory.getLogger(ReceiveStopTracePpacket.class.getName());

    @Override
    public int getOpcode() {
        return SC_Opcodes.stoptracepacket;
    }

    @Override
    public boolean read() {
        // nothing to read
        return true;
    }

    @Override
    public void run(ITcpClient client) {
        log.info("ReceiveStopTracePpacket");
        DataClient c = (DataClient)client;
        c.setBlocked(true);
    }
}
