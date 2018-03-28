package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
import com.mmobite.as.network.data_channel.packets.DataPacketsManager;
import com.mmobite.as.network.packet.WritePacket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class SendVersionPacket extends WritePacket {

    private DataClient client_;

    public SendVersionPacket(DataClient client) {
        client_ = client;
        setBuffer(client_.getChannel().alloc().buffer(256));
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.versionpacket;
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

        InetAddress i= null;
        try {
            i = InetAddress.getByName(client_.network_session_info_.ipv4);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int ipv4 = ByteBuffer.wrap(i.getAddress()).getInt();

        writeH(DataPacketsManager.protocol_version);
        writeH(0);
        writeD((int) client_.getGameSessionHandle());
        writeD(ipv4);
        writeS(client_.game_session_info_.account_name);
        writeS(client_.game_session_info_.character_name);
        writeD(client_.game_session_info_.char_dbid);
        writeD(client_.game_session_info_.account_dbid);
        writeS(ClientProperties.WORLD_GUID);
        writeS(client_.game_session_info_.hwid);
        writeD(client_.game_session_info_.online_time);
    }
}
