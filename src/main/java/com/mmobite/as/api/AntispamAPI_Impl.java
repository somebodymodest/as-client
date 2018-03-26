package com.mmobite.as.api;

import com.mmobite.as.network.ctrl_channel.client.CtrlTcpClient;
import com.mmobite.as.network.data_channel.client.DataTcpClient;

import java.util.HashMap;

public class AntispamAPI_Impl {

    private static final HashMap<Long, DataTcpClient> clientMap = new HashMap<>(3000);

    public static DataTcpClient getClient(long sessionId)
    {
        DataTcpClient result;

        synchronized (clientMap)
        {
            result = clientMap.get(sessionId);
        }

        return result;
    }

    protected static void addClient(long sessionId, DataTcpClient client)
    {
        synchronized (clientMap)
        {
            clientMap.put(sessionId, client);
        }
    }

    protected static void removeClient(long sessionId)
    {
        synchronized (clientMap)
        {
            clientMap.remove(sessionId);
        }
    }

    public static final boolean init(int L2ProtocolVersion)
    {
        CtrlTcpClient client = new CtrlTcpClient(L2ProtocolVersion);
        return (client != null) ? true : false;
    }

    public static final void openGameSession(long sessionId)
    {
        DataTcpClient client = new DataTcpClient();
        if (client == null)
            return;

        addClient(sessionId, client);
    }

    public static final void closeGameSession(long sessionId)
    {
        DataTcpClient client = getClient(sessionId);
        if (client == null)
            return;

        client.closeSession();  // try_reconnect = false

        removeClient(sessionId);
    }

    public static final void sendPacketData(long sessionId, int direction, byte[] data, int offset, int size)
    {
        DataTcpClient client = getClient(sessionId);
        if (client == null)
            return;

        client.sendPacketData(direction, data, offset, size);
    }

    public static final void sendHwid(long sessionId, String hwid)
    {
        DataTcpClient client = getClient(sessionId);
        if (client == null)
            return;

        client.sendHwid(hwid);
    }

}
