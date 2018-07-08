package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.OpcodeSC;
import com.mmobite.as.network.packet.ReadPacket;

public class ReceiveStopTraceOpcodePacket extends ReadPacket {

    private byte direction_;
    private int opcope_;
    private int opcode_ex_;

    @Override
    public int getOpcode() {
        return OpcodeSC.stoptraceopcodepacket;
    }

    @Override
    public boolean read() {
        direction_ = readC();
        opcope_ = readC();

        if (opcope_ == DataClient.getOpcodeEx(direction_))
            opcode_ex_ = readH();
        else
            opcode_ex_ = 0;

        return true;
    }

    @Override
    public void run(ITcpClient client) {
        //log.debug("ReceiveStopTraceOpcodePacket direction_[{}] opcope_[{}] opcode_ex_[{}]", direction_, opcope_, opcode_ex_);
        DataClient c = (DataClient)client;
        c.traceOpcode(direction_, opcope_, opcode_ex_, false);
    }
}
