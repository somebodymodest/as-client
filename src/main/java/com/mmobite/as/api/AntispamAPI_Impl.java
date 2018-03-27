package com.mmobite.as.api;

import com.mmobite.as.api.model.GameSessionInfo;
import com.mmobite.as.api.model.NetworkSessionInfo;
import com.mmobite.as.network.ctrl_channel.client.CtrlClient;
import com.mmobite.as.network.data_channel.client.DataClient;

import java.util.HashMap;

public class AntispamAPI_Impl {

    private static final HashMap<Long, DataClient> clientMap = new HashMap<>(3000);

    public static DataClient getClient(long sessionId)
    {
        DataClient result;

        synchronized (clientMap)
        {
            result = clientMap.get(sessionId);
        }

        return result;
    }

    protected static void addClient(long sessionId, DataClient client)
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
        CtrlClient client = new CtrlClient(L2ProtocolVersion);
        return (client != null) ? true : false;
    }

    public static final void openGameSession(long sessionId, NetworkSessionInfo info)
    {
        DataClient client = new DataClient(info);
        if (client == null)
            return;

        addClient(sessionId, client);
    }

    public static final void closeGameSession(long sessionId)
    {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;

        client.closeSession();  // try_reconnect = false

        removeClient(sessionId);
    }

    public static final void sendGameSessionInfo(long sessionId, GameSessionInfo info)
    {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;

        client.sendGameSessionInfo(info);
    }

    public static final void sendPacketData(long sessionId, int direction, byte[] data, int offset, int size)
    {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;

        client.sendPacketData(direction, data, offset, size);
    }

    public static final void sendHwid(long sessionId, String hwid)
    {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;

        client.sendHwid(hwid);
    }

}
