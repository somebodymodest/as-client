package com.mmobite.as.network.data_channel.packets;

import com.mmobite.as.network.data_channel.handlers.*;
import com.mmobite.as.network.packet.ReceiveDummyPacket;

import java.util.HashMap;
import java.util.Map;

public class DataPacketsManager {

    public static final int protocol_version = 0x03;

    protected final static Map<Short, Class<? extends ReceiveDummyPacket>> packets = new HashMap<>();

    static {
        packets.put(SC_Opcodes.dummypacket, ReceiveDummyPacket.class);
        packets.put(SC_Opcodes.pingpacket, ReceivePingPacket.class);
        packets.put(SC_Opcodes.pongpacket, ReceivePongPacket.class);
        packets.put(SC_Opcodes.stoptracepacket, ReceiveStopTracePpacket.class);
        packets.put(SC_Opcodes.starttracepacket, ReceiveStartTracePacket.class);
        packets.put(SC_Opcodes.stoptraceopcodepacket, ReceiveStopTraceOpcodePacket.class);
        packets.put(SC_Opcodes.starttraceopcodepacket, ReceiveStartTraceOpcodePacket.class);
    }

    public static ReceiveDummyPacket getPacket(short opcode) {
        try {
            return packets.get(opcode).newInstance();
        } catch (Exception ex) {
            return new ReceiveDummyPacket();
        }
    }

}
