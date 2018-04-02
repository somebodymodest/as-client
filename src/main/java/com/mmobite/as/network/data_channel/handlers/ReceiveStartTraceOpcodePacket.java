package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.SC_Opcodes;
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
        return SC_Opcodes.starttraceopcodepacket;
    }

    @Override
    public boolean read() {
        nDirection = readC();
        nOpcode = readC() & 0xFF;

        if (nOpcode == DataClient.get_opcode_ex(nDirection))
            nOpcodeEx = readH() & 0xFFFF;
        else
            nOpcodeEx = 0;

        return true;
    }

    @Override
    public void run(ITcpClient client) {
        log.info("ReceiveStartTraceOpcodePacket nOpcode[{}] nOpcodeEx[{}]", nOpcode, nOpcodeEx);
        DataClient c = (DataClient)client;
        c.traceOpcode(nDirection, nOpcode, nOpcodeEx, true);
    }
}
