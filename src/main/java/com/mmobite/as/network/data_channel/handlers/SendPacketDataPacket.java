package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.packet.WritePacket;

import java.nio.ByteBuffer;

public class SendPacketDataPacket extends WritePacket {
    private DataClient client_;
    private int direction_;
    private ByteBuffer buf_;

    public SendPacketDataPacket(DataClient client, int direction, ByteBuffer buf) {
        client_ = client;
        setBuffer(client_.getChannel().alloc().buffer(buf.position() + 16/*dddb*/));
        direction_ = direction;
        buf_ = buf;
    }

    @Override
    public void writeBody() {
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
        writeD(buf_.position());
        writeB(new byte[buf_.position()]);
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.packetdatapacket;
    }
}
