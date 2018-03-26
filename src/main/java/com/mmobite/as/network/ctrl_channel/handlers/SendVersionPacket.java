package com.mmobite.as.network.ctrl_channel.handlers;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.ctrl_channel.packets.CS_Opcodes;
import com.mmobite.as.network.ctrl_channel.packets.CtrlPacketsManager;
import com.mmobite.as.network.packet.SendDummyPacket;
import io.netty.channel.ChannelHandlerContext;

public class SendVersionPacket extends SendDummyPacket {

    private int L2ProtocolVersion_;

    public SendVersionPacket(ChannelHandlerContext ctx, int L2ProtocolVersion) {
        super(ctx);
        setOpcode(CS_Opcodes.versionpacket);
        L2ProtocolVersion_ = L2ProtocolVersion;
    }

    @Override
    public void writeBody() {
        writeH(CtrlPacketsManager.protocol_version);
        writeH(0);
        writeD(L2ProtocolVersion_);
        writeS("license test");
        writeS("email test");
        writeD(0);
        writeS(ClientProperties.WORLD_GUID);
    }
}
