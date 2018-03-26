package com.mmobite.as.network.ctrl_channel.packets;

import com.mmobite.as.network.ctrl_channel.handlers.ReceivePingPacket;
import com.mmobite.as.network.ctrl_channel.handlers.ReceivePongPacket;
import com.mmobite.as.network.packet.ReceiveDummyPacket;

import java.util.HashMap;
import java.util.Map;

public class CtrlPacketsManager {

    public static final int protocol_version = 0x01;

    protected final static Map<Short, Class<? extends ReceiveDummyPacket>> packets = new HashMap<>();

    static {
        packets.put(SC_Opcodes.dummypacket, ReceiveDummyPacket.class);
        packets.put(SC_Opcodes.pingpacket, ReceivePingPacket.class);
        packets.put(SC_Opcodes.pongpacket, ReceivePongPacket.class);
    }

    public static ReceiveDummyPacket getPacket(short opcode) {
        try {
            return packets.get(opcode).newInstance();
        } catch (Exception ex) {
            return new ReceiveDummyPacket();
        }
    }

}
