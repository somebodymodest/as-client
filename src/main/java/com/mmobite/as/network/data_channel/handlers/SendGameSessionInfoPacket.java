package com.mmobite.as.network.data_channel.handlers;

import com.mmobite.as.network.data_channel.client.DataClient;
import com.mmobite.as.network.data_channel.packets.CS_Opcodes;
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
            s - sAccountName[ACCOUNT_NAME_SIZE]
            s - sCharName[CHARACTER_NAME_SIZE]
            d - nCharDbId
            d - nAccountId
            S - hwid[DB_HWID_SIZE]
            d - player total online time
        */
        writeS(client_.game_session_info_.account_name);
        writeS(client_.game_session_info_.character_name);
        writeD(client_.game_session_info_.char_dbid);
        writeD(client_.game_session_info_.account_dbid);
        writes(client_.game_session_info_.hwid);
        writeD(client_.game_session_info_.online_time);
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.gamesessioninfopacket;
    }
}
