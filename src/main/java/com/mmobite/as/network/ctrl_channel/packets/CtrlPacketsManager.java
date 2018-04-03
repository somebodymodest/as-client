package com.mmobite.as.network.ctrl_channel.packets;

import com.mmobite.as.network.ctrl_channel.handlers.*;
import com.mmobite.as.network.packet.ReadPacket;

import java.util.HashMap;
import java.util.Map;

public class CtrlPacketsManager {

    public static final int protocol_version = 0x02;

    protected final static Map<Integer, Class<? extends ReadPacket>> packets = new HashMap<>();

    static {
        packets.put(OpcodeSC.dummypacket, ReceiveDummyPacket.class);
        packets.put(OpcodeSC.pingpacket, ReceivePingPacket.class);
        packets.put(OpcodeSC.pongpacket, ReceivePongPacket.class);
    }

    public static ReadPacket getPacket(int opcode) {
        try {
            return packets.get(opcode).newInstance();
        } catch (Exception ex) {
            return new ReceiveDummyPacket();
        }
    }

}
