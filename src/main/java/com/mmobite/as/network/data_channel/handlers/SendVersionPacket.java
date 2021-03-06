package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.client.AntiSpamClientProperties;
import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.OpcodeCS;
import com.mmobite.as.network.data_channel.packets.DataPacketsManager;
import com.mmobite.as.network.packet.WritePacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SendVersionPacket extends WritePacket {

    private DataClient client_;

    public SendVersionPacket(DataClient client) {
        super();
        client_ = client;
    }

    @Override
    public int getOpcode() {
        return OpcodeCS.versionpacket;
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
        // d - player level
        writeH(DataPacketsManager.protocol_version);
        writeH(0);
        writeD((int) client_.getGameSessionHandle());
        writeD(string_to_in_addr(client_.network_session_info_.ipv4));
        writeS(client_.game_session_info_.account_name);
        writeS(client_.game_session_info_.character_name);
        writeD(client_.game_session_info_.char_dbid);
        writeD(client_.game_session_info_.account_dbid);
        writeS(AntiSpamClientProperties.WORLD_GUID);
        writes(client_.game_session_info_.hwid);
        writeD(client_.game_session_info_.online_time);
        writeD(client_.game_session_info_.char_level);
    }

    int string_to_in_addr(String ipv4)
    {
        InetAddress i = null;
        try {
            i = InetAddress.getByName(ipv4);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        ByteBuffer bb = ByteBuffer.wrap(i.getAddress());
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }
}
