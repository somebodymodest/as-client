package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.ctrl_channel.client.CtrlClient;
import com.mmobite.as.network.ctrl_channel.packets.CS_Opcodes;
import com.mmobite.as.network.ctrl_channel.packets.CtrlPacketsManager;
import com.mmobite.as.network.packet.WritePacket;

public class SendVersionPacket extends WritePacket {

    private CtrlClient client_;

    public SendVersionPacket(CtrlClient client) {
        client_ = client;
        setBuffer(client_.getChannel().alloc().buffer(256));
    }

    @Override
    public short getOpcode() {
        return CS_Opcodes.versionpacket;
    }

    @Override
    public void writeBody() {
        writeH(CtrlPacketsManager.protocol_version);
        writeH(0);
        writeD(client_.L2ProtocolVersion_);
        writeS("license test");
        writeS("email test");
        writeD(0);
        writeS(ClientProperties.WORLD_GUID);
    }

}
