package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.data_channel.packets.DataPacketsManager;
import com.mmobite.as.network.packet.SendDummyPacket;
import io.netty.channel.ChannelHandlerContext;

public class SendVersionPacket extends SendDummyPacket {

    private DataClient client_;

    public SendVersionPacket(DataClient client) {
        super(client.getChannel());
        setOpcode(CS_Opcodes.versionpacket);
        client_ = client;
    }

    @Override
    public void writeBody() {

        // c - opcode
        // h - nTraceProtocolId
        // h - nWorldId !!! world_id should be equal 0
        // d - nUserSocketObjectId
        // b(4 bytes) - IP addr(in_addr struct)
        // s - sAccountName[ACCOUNT_NAME_SIZE]
        // s - sCharName[CHARACTER_NAME_SIZE]
        // d - nCharDbId
        // d - nAccountId
        // s - sWorldGUID[DB_WORLD_GUID_SIZE]
        // s - sHwid[DB_HWID_SIZE]
        // d - player total online time

        writeH(DataPacketsManager.protocol_version);
        writeH(0);
        writeD((int) client_.getGameSessionHandle());
        writeD(0);
        writeS("account test");
        writeS("char test");
        writeD(0);
        writeD(0);
        writeS(ClientProperties.WORLD_GUID);
        writeS("");
        writeD(0);
    }
}
