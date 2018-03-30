package com.mmobite.as.network.ctrl_channel.packets;

import com.mmobite.as.network.ctrl_channel.handlers.*;
import com.mmobite.as.network.packet.ReadPacket;

import java.util.HashMap;
import java.util.Map;

public class CtrlPacketsManager {

    public static final int protocol_version = 0x02;

    protected final static Map<Short, Class<? extends ReadPacket>> packets = new HashMap<>();

    static {
        packets.put(SC_Opcodes.dummypacket, ReceiveDummyPacket.class);
        packets.put(SC_Opcodes.pingpacket, ReceivePingPacket.class);
        packets.put(SC_Opcodes.pongpacket, ReceivePongPacket.class);
    }

    public static ReadPacket getPacket(short opcode) {
        try {
            return packets.get(opcode).newInstance();
        } catch (Exception ex) {
            return new ReceiveDummyPacket();
        }
    }

}
