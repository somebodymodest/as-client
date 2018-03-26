package com.mmobite.as.network.managers;

import com.mmobite.as.network.packet.ReceiveDummyPacket;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ivan on 23.03.2018.
 */
public class PacketManager {

    protected final static Map<Short, Class<? extends ReceiveDummyPacket>> packets = new HashMap<>();

    public static ReceiveDummyPacket getPacket(short opcode) {
        try {
            return packets.get(opcode).newInstance();
        } catch (Exception ex) {
            return new ReceiveDummyPacket();
        }
    }
}
