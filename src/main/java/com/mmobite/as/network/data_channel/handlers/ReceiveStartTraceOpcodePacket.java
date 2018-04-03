package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.OpcodeSC;
import com.mmobite.as.network.packet.ReadPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiveStartTraceOpcodePacket extends ReadPacket {

    private static Logger log = LoggerFactory.getLogger(ReceiveStartTraceOpcodePacket.class.getName());
    private byte nDirection;
    private int nOpcode;
    private int nOpcodeEx;

    @Override
    public int getOpcode() {
        return OpcodeSC.starttraceopcodepacket;
    }

    @Override
    public boolean read() {
        nDirection = readC();
        nOpcode = readC() & 0xFF;

        if (nOpcode == DataClient.getOpcodeEx(nDirection))
            nOpcodeEx = readH() & 0xFFFF;
        else
            nOpcodeEx = 0;

        return true;
    }

    @Override
    public void run(ITcpClient client) {
        log.info("ReceiveStartTraceOpcodePacket nDirection[{}] nOpcode[{}] nOpcodeEx[{}]", nDirection, nOpcode, nOpcodeEx);
        DataClient c = (DataClient)client;
        c.traceOpcode(nDirection, nOpcode, nOpcodeEx, true);
    }
}
