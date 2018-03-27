package com.mmobite.as.api;

import com.mmobite.as.api.model.GameSessionInfo;
import com.mmobite.as.api.model.NetworkSessionInfo;
import com.mmobite.as.network.ctrl_channel.client.CtrlClient;
import com.mmobite.as.network.data_channel.client.DataClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AntispamAPI_Impl {

    private static final Map<Long, DataClient> clientMap = new ConcurrentHashMap<>(3000);
    private static CtrlClient client;

    public static DataClient getClient(long sessionId)
    {
        return clientMap.get(sessionId);
    }

    protected static void addClient(long sessionId, DataClient client)
    {
        clientMap.put(sessionId, client);
    }

    protected static DataClient removeClient(long sessionId)
    {
        return clientMap.remove(sessionId);
    }

    /**
     * TODO: Need safe?
     * @param L2ProtocolVersion -
     * @return -
     */
    public static final boolean init(int L2ProtocolVersion)
    {
        if(client == null)
            client = new CtrlClient(L2ProtocolVersion);
        return true;
    }

    public static final void openGameSession(long sessionId, NetworkSessionInfo info)
    {
        DataClient client = new DataClient(info);
        addClient(sessionId, client);
    }

    public static final void closeGameSession(long sessionId)
    {
        DataClient client = removeClient(sessionId);
        if (client == null)
            return;

        client.closeSession();  // try_reconnect = false
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
