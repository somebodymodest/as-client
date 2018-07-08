package com.mmobite.as.network.data_channel.packets;

import com.mmobite.as.network.data_channel.handlers.*;
import com.mmobite.as.network.packet.ReadPacket;
import java.util.HashMap;
import java.util.Map;

public class DataPacketsManager {

    public static final int protocol_version = 0x04;

    protected final static Map<Integer, Class<? extends ReadPacket>> packets = new HashMap<>();

    static {
        packets.put(OpcodeSC.pingpacket, ReceivePingPacket.class);
        packets.put(OpcodeSC.pongpacket, ReceivePongPacket.class);
        packets.put(OpcodeSC.stoptracepacket, ReceiveStopTracePpacket.class);
        packets.put(OpcodeSC.starttracepacket, ReceiveStartTracePacket.class);
        packets.put(OpcodeSC.stoptraceopcodepacket, ReceiveStopTraceOpcodePacket.class);
        packets.put(OpcodeSC.starttraceopcodepacket, ReceiveStartTraceOpcodePacket.class);
    }

    public static ReadPacket getPacket(int opcode) {
        try {
            return packets.get(opcode).newInstance();
        } catch (Exception ex) {
            return new ReceiveDummyPacket();
        }
    }

}
