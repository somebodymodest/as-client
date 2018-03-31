package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class SendPacketDataPacket extends WritePacket {

    private static Logger log = LoggerFactory.getLogger(SendVersionPacket.class.getName());
    private int direction_;
    private ByteBuffer pkt_;

    public SendPacketDataPacket(int direction, ByteBuffer pkt) {
        super(pkt.remaining() + 16/*dddb*/);
        direction_ = direction;
        pkt_ = pkt;
    }

    @Override
    public void writeBody() {
        log.debug("SendPacketDataPacket: writeBody start");
        /*
        format: "cdddb"
            c - opcode
            d - nDirection (clientgame = 6, gameclient = 7)
            d - nTickCount
            d - nPacketSize
            b(nPacketSize bytes) - la2-packet (first byte is opcode)
        */
        writeD(direction_);
        writeD((int) System.currentTimeMillis());
        writeD(pkt_.remaining());
        byte[] arr = new byte[pkt_.remaining()];
        pkt_.get(arr, pkt_.position(), pkt_.limit());
        writeB(arr);
        log.debug("SendPacketDataPacket: writeBody end");
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.packetdatapacket;
    }
}
