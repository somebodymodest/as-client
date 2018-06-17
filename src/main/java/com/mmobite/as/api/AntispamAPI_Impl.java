package com.mmobite.as.api;

import com.mmobite.as.api.model.GameSessionInfo;
import com.mmobite.as.api.model.NetworkSessionInfo;
import com.mmobite.as.network.ctrl_channel.client.CtrlClient;
import com.mmobite.as.network.data_channel.client.DataClient;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class AntispamAPI_Impl {

    private static final Map<Long, DataClient> clientMap = new ConcurrentHashMap<>(3000);
    private static final AtomicBoolean is_initialized = new AtomicBoolean(false);
    private static CtrlClient client = null;

    public static boolean isConnectedToTraceServer() {
        return client != null ? client.isConnected() : false;
    }

    public static DataClient getClient(long sessionId) {
        return clientMap.get(sessionId);
    }

    protected static void addClient(long sessionId, DataClient client) {
        clientMap.put(sessionId, client);
    }

    protected static DataClient removeClient(long sessionId) {
        return clientMap.remove(sessionId);
    }

    /**
     * TODO: Need safe? It`s safe now?
     *
     * @param L2ProtocolVersion -
     * @return -
     */
    public static final boolean init(int L2ProtocolVersion) {
        if (is_initialized.compareAndSet(false, true))
            client = new CtrlClient(L2ProtocolVersion);
        return true;
    }

    public static long openGameSession(NetworkSessionInfo info) {
        DataClient client = new DataClient(info);
        long game_session_handle = client.getGameSessionHandle();
        addClient(game_session_handle, client);
        return game_session_handle;
    }

    public static final void closeGameSession(long sessionId) {
        DataClient client = removeClient(sessionId);
        if (client == null)
            return;
        client.closeSession();
    }

    public static final void sendGameSessionInfo(long sessionId, GameSessionInfo info) {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;
        client.sendGameSessionInfo(info);
    }

    public static final void sendPacketData(long sessionId, int direction, ByteBuffer buf) {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;
        client.sendPacketData(direction, buf);
    }

    public static final void sendPacketData(long sessionId, int direction, byte[] buf, int offset, int size) {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;
        client.sendPacketData(direction, buf, offset, size);
    }

    public static final void sendHwid(long sessionId, String hwid) {
        DataClient client = getClient(sessionId);
        if (client == null)
            return;
        client.sendHwid(hwid);
    }

}
