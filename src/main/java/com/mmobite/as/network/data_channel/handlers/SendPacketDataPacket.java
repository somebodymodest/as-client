package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class SendPacketDataPacket extends WritePacket {

    private static Logger log = LoggerFactory.getLogger(SendVersionPacket.class.getName());
    private int direction_;
    private byte[] pkt_;
    private int size_;

    public SendPacketDataPacket(int direction, ByteBuffer pkt, int size) {
        super(size + 16/*dddb*/);
        direction_ = direction;
        pkt_ = new byte[size]; int pos = pkt.position(); pkt.get(pkt_, 0, size); pkt.position(pos);
        size_ = size;
    }

    @Override
    public void writeBody() {
        int pkt_opcode = pkt_[0] & 0xFF;
        int pkt_length = size_;
        //log.info("SendPacketDataPacket: writeBody start. direction[{}] opcode[{}] size[{}]", direction_, pkt_opcode, pkt_length );
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
        writeD(size_);
        writeB(pkt_);
        log.info("SendPacketDataPacket: writeBody end. direction[{}] opcode[{}] size[{}]", direction_, pkt_opcode, pkt_length );
    }

    @Override
    public int getOpcode() {
        return CS_Opcodes.packetdatapacket;
    }
}
