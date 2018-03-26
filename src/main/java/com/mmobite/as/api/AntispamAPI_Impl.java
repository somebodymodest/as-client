package com.mmobite.as.api;

import com.mmobite.as.network.ctrl_channel.client.CtrlTcpClient;

public class AntispamAPI_Impl {

    public static final boolean init(int L2ProtocolVersion)
    {
        CtrlTcpClient client = new CtrlTcpClient(L2ProtocolVersion);
        return (client != null) ? true : false;
    }

    public static final void openGameSession(long sessionId)
    {
//        DataTraceClient client = new DataTraceClient();
//        if (client.IsNull())
//            return;
//
//        DataTraceClientManager.registerClient(sessionId, client);
    }

    public static final void closeGameSession(long sessionId)
    {
//        DataTraceClient client = DataTraceClientManager.getClient(sessionId);
//        if (client.IsNull())
//            return;
//
//        client.closeSession();  // try_reconnect = false
//
//        DataTraceClientManager.unregisterClient(sessionId);
    }

    public static final void sendPacketData(long sessionId, int direction, byte[] data, int offset, int size)
    {
//        DataTraceClient client = DataTraceClientManager.getClient(sessionId);
//        if (client.IsNull())
//            return;
//
//        client.sendPacketData(direction, data, offset, size);
    }

    public static final void sendHwid(long sessionId, String hwid)
    {
//        DataTraceClient client = DataTraceClientManager.getClient(sessionId);
//        if (client.IsNull())
//            return;
//
//        client.sendHwid(hwid);
    }

}
