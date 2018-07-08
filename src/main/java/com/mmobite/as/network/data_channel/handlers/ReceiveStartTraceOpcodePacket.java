package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.OpcodeSC;
import com.mmobite.as.network.packet.ReadPacket;

public class ReceiveStartTraceOpcodePacket extends ReadPacket {

    private byte direction_;
    private int opcode_;
    private int opcode_ex_;

    @Override
    public int getOpcode() {
        return OpcodeSC.starttraceopcodepacket;
    }

    @Override
    public boolean read() {
        direction_ = readC();
        opcode_ = readC() & 0xFF;

        if (opcode_ == DataClient.getOpcodeEx(direction_))
            opcode_ex_ = readH() & 0xFFFF;
        else
            opcode_ex_ = 0;

        return true;
    }

    @Override
    public void run(ITcpClient client) {
        //log.debug("ReceiveStartTraceOpcodePacket direction_[{}] opcode_[{}] opcode_ex_[{}]", direction_, opcode_, opcode_ex_);
        DataClient c = (DataClient)client;
        c.traceOpcode(direction_, opcode_, opcode_ex_, true);
    }
}
