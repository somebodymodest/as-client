package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.ctrl_channel.client.CtrlClient;
import com.mmobite.as.network.ctrl_channel.packets.OpcodeCS;
import com.mmobite.as.network.ctrl_channel.packets.CtrlPacketsManager;
import com.mmobite.as.network.packet.WritePacket;

public class SendVersionPacket extends WritePacket {

    private CtrlClient client_;

    public SendVersionPacket(CtrlClient client) {
        super();
        client_ = client;
    }

    @Override
    public int getOpcode() {
        return OpcodeCS.versionpacket;
    }

    @Override
    public void writeBody() {
        /*
        format: "chhdssds"
        c - opcode
        h - nTraceProtocolId
        h - nWorldId
        d - nGameProtocolId
        s - sLicenseUserName[EMAIL_SIZE]
        s - sLicenseEmail[EMAIL_SIZE]
        d - server type (0-PTS, 1-Java)
        s - sWorldGUID[DB_WORLD_GUID_SIZE]
        */
        writeH(CtrlPacketsManager.protocol_version);
        writeH(0);
        writeD(client_.L2ProtocolVersion_);
        writeS("license test");
        writeS("email test");
        writeD(1);
        writeS(ClientProperties.WORLD_GUID);
    }
}
