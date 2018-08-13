package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.OpcodeCS;
import com.mmobite.as.network.packet.WritePacket;

public class SendGameSessionInfoPacket extends WritePacket {

    private DataClient client_;

    public SendGameSessionInfoPacket(DataClient client)
    {
        super();
        client_ = client;
    }

    @Override
    public void writeBody() {
        /*
        format: "csd"
            c - opcode
            S - sAccountName[ACCOUNT_NAME_SIZE] UTF-16LE
            S - sCharName[CHARACTER_NAME_SIZE] UTF-16LE
            d - nCharDbId
            d - nAccountId
            s - hwid[DB_HWID_SIZE] UTF-8
            d - player total online time in seconds
            d - player level
        */
        writeS(client_.game_session_info_.account_name);
        writeS(client_.game_session_info_.character_name);
        writeD(client_.game_session_info_.char_dbid);
        writeD(client_.game_session_info_.account_dbid);
        writes(client_.game_session_info_.hwid);
        writeD(client_.game_session_info_.online_time);
        writeD(client_.game_session_info_.char_level);
    }

    @Override
    public int getOpcode() {
        return OpcodeCS.gamesessioninfopacket;
    }
}
