package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.packets.OpcodeCS;
import com.mmobite.as.network.packet.WritePacket;

public class SendPacketDataPacket extends WritePacket {

    private int direction_;
    private byte[] data_;
    private int size_;

    public SendPacketDataPacket(int direction, byte[] data, int size) {
        super(size + 16/*dddb*/);
        direction_ = direction;
        data_ = data;
        size_ = size;
    }

    @Override
    public void writeBody() {
        //int pkt_opcode = data_[0] & 0xFF;
        //int pkt_length = size_;
        //log.debug("SendPacketDataPacket: writeBody start. direction[{}] opcode[{}] size[{}]", direction_, pkt_opcode, pkt_length );
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
        writeB(data_);
        //log.debug("SendPacketDataPacket: writeBody end. direction[{}] opcode[{}] size[{}]", direction_, pkt_opcode, pkt_length );
    }

    @Override
    public int getOpcode() {
        return OpcodeCS.packetdatapacket;
    }
}
