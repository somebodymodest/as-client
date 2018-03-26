package com.mmobite.as.api;

import com.mmobite.as.api.model.GameSessionInfo;
import com.mmobite.as.api.model.NetworkSessionInfo;

public class AntispamAPI {

    public static final boolean init(int L2ProtocolVersion) {
        return AntispamAPI_Impl.init(L2ProtocolVersion);
    }

    public static final void openGameSession(long sessionId, NetworkSessionInfo info) {
        AntispamAPI_Impl.openGameSession(sessionId, info);
    }

    public static final void closeGameSession(long sessionId) {
        AntispamAPI_Impl.closeGameSession(sessionId);
    }

    public static final void sendGameSessionInfo(long sessionId, GameSessionInfo info) {
        AntispamAPI_Impl.sendGameSessionInfo(sessionId, info);
    }

    public static final void sendPacketData(long sessionId, int direction, byte[] data, int offset, int size) {
        AntispamAPI_Impl.sendPacketData(sessionId, direction, data, offset, size);
    }

    public static final void sendHwid(long sessionId, String hwid) {
        AntispamAPI_Impl.sendHwid(sessionId, hwid);
    }
}
