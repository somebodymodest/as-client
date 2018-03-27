package com.mmobite.as.network.data_channel.client;

import com.mmobite.as.api.AntispamAPI_Impl;
import com.mmobite.as.api.model.Direction;
import com.mmobite.as.api.model.GameSessionInfo;
import com.mmobite.as.api.model.NetworkSessionInfo;
import com.mmobite.as.api.model.PacketEx;
import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.data_channel.handlers.SendVersionPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class DataClient {

    // static
    private static Logger log = LoggerFactory.getLogger(DataClient.class.getName());
    private final static EventLoopGroup loop_ = new NioEventLoopGroup();
    private final static DataClientHandler handler_ = new DataClientHandler();
    private final AtomicLong game_session_counter_ = new AtomicLong(0);

    // network
    private final Bootstrap bs_;
    public final String HOST_;
    public final int PORT_;
    public final int L2ProtocolVersion_;
    private ChannelHandlerContext ctx_;
    private NetworkSessionInfo network_session_info_;
    private boolean try_reconnect_ = true;

    // game-protocol
    private boolean is_blocked_ = true;
    private long game_session_handle_;
    boolean[] m_aTracedOpcode_CS = new boolean[0xFF];
    boolean[] m_aTracedOpcodeEx_CS = new boolean[0xFF];
    boolean[] m_aTracedOpcode_SC = new boolean[0xFF];
    boolean[] m_aTracedOpcodeEx_SC = new boolean[0xFF];

    public DataClient(NetworkSessionInfo info) {
        HOST_ = ClientProperties.SERVER_ADDR;
        PORT_ = ClientProperties.PORT_DATA;
        L2ProtocolVersion_ = 0;
        network_session_info_ = info;

        bs_ = new Bootstrap();
        bs_.group(loop_)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(HOST_, PORT_)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new DataClientInitializer(handler_));

        handler_.setClient(this);
        game_session_handle_ = game_session_counter_.incrementAndGet();

        tryConnect();
    }

    public void tryConnect() {
        if (AntispamAPI_Impl.isConnectedToTraceServer()) {
            log.debug("Try to connect to: " + HOST_ + ':' + PORT_);
            bs_.connect();
        } else {
            log.debug("Try to connect to: wait for ctrl channel");
            loop_.schedule(new Runnable() {
                @Override
                public void run() {
                    tryConnect();
                }
            }, ClientProperties.RECONNECT_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    public void setChannel(ChannelHandlerContext ctx) {
        ctx_ = ctx;
    }

    public ChannelHandlerContext getChannel() {
        return ctx_;
    }

    public void sendVersionPacket() {
        new SendVersionPacket(ctx_, L2ProtocolVersion_).sendPacket();
    }

    public void closeSession() {
    }

    public void sendGameSessionInfo(GameSessionInfo info) {
    }

    public void sendPacketData(int direction, byte[] data, int offset, int size) {
    }

    public void sendHwid(String hwid) {
    }

    public boolean isBlocked() {
        return is_blocked_;
    }

    public void setBlocked(boolean onOff) {
        is_blocked_ = onOff;
    }

    public boolean isBlocked(int direction, short nOpCode, short nOpCodeEx) {
        if (!isBlocked()) return false;

        if (direction == Direction.clientgame.value) {
            if (nOpCode == PacketEx.clientgame_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx)) return true;
                if (m_aTracedOpcodeEx_CS[nOpCodeEx]) return false;
            } else {
                if (!isValidOpcode(direction, nOpCode)) return true;
                if (m_aTracedOpcode_CS[nOpCode]) return false;
            }
        } else if (direction == Direction.gameclient.value) {
            if (nOpCode == PacketEx.gameclient_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx)) return true;
                if (m_aTracedOpcodeEx_SC[nOpCodeEx]) return false;
            } else {
                if (!isValidOpcode(direction, nOpCode)) return true;
                if (m_aTracedOpcode_SC[nOpCode]) return false;
            }
        }

        return true;
    }

    public void traceOpcode(int direction, short nOpCode, short nOpCodeEx, boolean nEnable) {
        if (direction == Direction.clientgame.value) {
            if (nOpCode == PacketEx.clientgame_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx)) return;
                m_aTracedOpcodeEx_CS[nOpCodeEx] = nEnable;
            } else {
                if (!isValidOpcode(direction, nOpCode)) return;
                m_aTracedOpcode_CS[nOpCode] = nEnable;
            }
        } else if (direction == Direction.gameclient.value) {
            if (nOpCode == PacketEx.gameclient_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx)) return;
                m_aTracedOpcodeEx_SC[nOpCodeEx] = nEnable;
            } else {
                if (!isValidOpcode(direction, nOpCode)) return;
                m_aTracedOpcode_SC[nOpCode] = nEnable;
            }
        }
    }

    public boolean isValidOpcode(int direction, short nOpCode) {
        if (direction == Direction.clientgame.value) {
            return !(nOpCode < 0 || nOpCode >= m_aTracedOpcode_CS.length);
        } else if (direction == Direction.gameclient.value) {
            return !(nOpCode < 0 || nOpCode >= m_aTracedOpcode_SC.length);
        }
        return false;
    }

    public boolean isValidOpcodeEx(int direction, short nOpCodeEx) {
        if (direction == Direction.clientgame.value) {
            return !(nOpCodeEx < 0 || nOpCodeEx >= m_aTracedOpcodeEx_CS.length);
        } else if (direction == Direction.gameclient.value) {
            return !(nOpCodeEx < 0 || nOpCodeEx >= m_aTracedOpcodeEx_SC.length);
        }
        return false;
    }

    public long getGameSessionHandle() {
        return game_session_handle_;
    }
}