package com.mmobite.as.api;

import com.mmobite.as.api.model.GameSessionInfo;
import com.mmobite.as.api.model.NetworkSessionInfo;
import com.mmobite.as.network.client.AntiSpamClientProperties;

import java.nio.ByteBuffer;

public class AntispamAPI {
    /**
     *
     * @param configPath, for example "client.properties"
     * @param L2ProtocolVersion
     * @return
     */
    public static final boolean init(String configPath, int L2ProtocolVersion) {
        AntiSpamClientProperties.load(configPath);
        return AntispamAPI_Impl.init(L2ProtocolVersion);
    }

    /**
     * @param info
     * @return session handle
     */
    public static final long openGameSession(NetworkSessionInfo info) {
        if (!AntiSpamClientProperties.ENABLED)
            return 0;
        return AntispamAPI_Impl.openGameSession(info);
    }

    public static final void closeGameSession(long sessionId) {
        // !!! opened connections must be closed anyway
        //if (!AntiSpamClientProperties.ENABLED)
        //    return;
        AntispamAPI_Impl.closeGameSession(sessionId);
    }

    public static final void sendGameSessionInfo(long sessionId, GameSessionInfo info) {
        if (!AntiSpamClientProperties.ENABLED)
            return;
        AntispamAPI_Impl.sendGameSessionInfo(sessionId, info);
    }

    public static final void sendPacketData(long sessionId, int direction, ByteBuffer buf) {
        if (!AntiSpamClientProperties.ENABLED)
            return;
        AntispamAPI_Impl.sendPacketData(sessionId, direction, buf);
    }

    public static final void sendPacketData(long sessionId, int direction, byte[] buf, int offset, int size) {
        if (!AntiSpamClientProperties.ENABLED)
            return;
        AntispamAPI_Impl.sendPacketData(sessionId, direction, buf, offset, size);
    }

    public static final void sendHwid(long sessionId, String hwid) {
        if (!AntiSpamClientProperties.ENABLED)
            return;
        AntispamAPI_Impl.sendHwid(sessionId, hwid);
    }
}
