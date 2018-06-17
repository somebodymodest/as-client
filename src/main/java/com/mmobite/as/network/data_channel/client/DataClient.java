package com.mmobite.as.network.data_channel.client;

import com.mmobite.as.api.AntispamAPI_Impl;
import com.mmobite.as.api.model.Direction;
import com.mmobite.as.api.model.GameSessionInfo;
import com.mmobite.as.api.model.NetworkSessionInfo;
import com.mmobite.as.network.client.AntiSpamClientProperties;
import com.mmobite.as.network.client.ITcpClient;
import com.mmobite.as.network.data_channel.handlers.SendGameSessionInfoPacket;
import com.mmobite.as.network.data_channel.handlers.SendHwidPacket;
import com.mmobite.as.network.data_channel.handlers.SendPacketDataPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.mmobite.as.api.model.Direction.gameclient;
import static com.mmobite.as.api.model.PacketEx.clientgame_opcode_ex;
import static com.mmobite.as.api.model.PacketEx.gameclient_opcode_ex;

public class DataClient extends ITcpClient {

    // static
    private static Logger log = LoggerFactory.getLogger(DataClient.class.getName());
    private final static EventLoopGroup loop_ = new NioEventLoopGroup();
    private final static DataClientHandler handler_ = new DataClientHandler();
    private final static AtomicLong game_session_counter_ = new AtomicLong(0);

    // network
    private final Bootstrap bs_;
    public final String HOST_;
    public final int PORT_;
    public final int L2ProtocolVersion_;
    public NetworkSessionInfo network_session_info_;
    public GameSessionInfo game_session_info_;
    private boolean try_reconnect_ = true;

    // game-protocol
    private boolean is_blocked_ = false;
    private long game_session_handle_;
    boolean[] m_aTracedOpcode_CS = new boolean[0xFF];
    boolean[] m_aTracedOpcodeEx_CS = new boolean[0xFF];
    boolean[] m_aTracedOpcode_SC = new boolean[0xFF];
    boolean[] m_aTracedOpcodeEx_SC = new boolean[0xFF];

    public DataClient(NetworkSessionInfo info) {
        HOST_ = AntiSpamClientProperties.SERVER_ADDR;
        PORT_ = AntiSpamClientProperties.PORT_DATA;
        L2ProtocolVersion_ = 0;
        network_session_info_ = info;
        game_session_info_ = new GameSessionInfo();

        bs_ = new Bootstrap();
        bs_.group(loop_)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(HOST_, PORT_)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new DataClientInitializer(handler_));

        handler_.setClient(this);
        game_session_handle_ = game_session_counter_.incrementAndGet();

        tryConnect();
    }

    public EventLoopGroup getLoop() {
        return loop_;
    }

    public void tryConnect() {

        if (AntispamAPI_Impl.isConnectedToTraceServer()) {
            //log.info("Try to connect to: " + HOST_ + ':' + PORT_);
            bs_.connect();
        } else {
            //log.info("Try to connect to: wait for ctrl channel");
            loop_.schedule(new Runnable() {
                @Override
                public void run() {
                    tryConnect();
                }
            }, AntiSpamClientProperties.RECONNECT_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    public void closeSession() {
        setTryReconnect(false);

        Channel ch = getChannel();
        if (ch != null)
            ch.close();
    }

    public void sendGameSessionInfo(GameSessionInfo info) {
        game_session_info_ = info;
        sendPacket(new SendGameSessionInfoPacket(this));
    }

    public void sendPacketData(int direction, ByteBuffer buf) {
        final int size = buf.remaining();
        final byte[] data = new byte[size];
        int pos = buf.position();
        buf.get(data, 0, size);
        buf.position(pos);

        sendPacketData(direction, data, size);
    }

    public void sendPacketData(int direction, byte[] buf, int offset, int size) {
        if(offset + size > buf.length)
            throw new InvalidParameterException();

        final byte[] data = Arrays.copyOfRange(buf, offset, offset + size);

        sendPacketData(direction, data, size);
    }

    private void sendPacketData(int direction, byte[] data, int size) {
        int nOpCode = (data[0] & 0xff);
        int nOpCodeEx = (nOpCode == getOpcodeEx(direction)) ? (data[1] & 0xFFFF) : 0; //TODO: we need get short from bytes array
        if (isBlocked(direction, nOpCode, nOpCodeEx))
            return;

        //log.info("sendPacketData: direction[{}] OpCode[{}:{}] Size[{}]", direction, nOpCode, nOpCodeEx, size);
        sendPacket(new SendPacketDataPacket(direction, data, size));
    }

    public void sendHwid(String hwid) {
        sendPacket(new SendHwidPacket(hwid));
    }

    public boolean isBlocked() {
        return is_blocked_;
    }

    public void setBlocked(boolean onOff) {
        is_blocked_ = onOff;
    }

    public boolean isBlocked(int direction, int nOpCode, int nOpCodeEx) {
        boolean global_block = isBlocked();
        //log.info("isBlocked: global_block[{}] direction[{}] OpCode[{}:{}]", global_block, direction, nOpCode, nOpCodeEx);
        if (!global_block) return false;

        boolean opcode_block = true;

        if (direction == Direction.clientgame.value) {
            if (nOpCode == clientgame_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx))
                    opcode_block = true;
                else if (m_aTracedOpcodeEx_CS[nOpCodeEx])
                    opcode_block = false;
            } else {
                if (!isValidOpcode(direction, nOpCode))
                    opcode_block = true;
                else if (m_aTracedOpcode_CS[nOpCode])
                    opcode_block = false;
            }
        } else if (direction == gameclient.value) {
            if (nOpCode == gameclient_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx))
                    opcode_block = true;
                else if (m_aTracedOpcodeEx_SC[nOpCodeEx])
                    opcode_block = false;
            } else {
                if (!isValidOpcode(direction, nOpCode))
                    opcode_block = true;
                else if (m_aTracedOpcode_SC[nOpCode])
                    opcode_block = false;
            }
        }
        //log.info("isBlocked: opcode_block[{}] direction[{}] OpCode[{}:{}]", opcode_block, direction, nOpCode, nOpCodeEx);
        return opcode_block;
    }

    public void traceOpcode(int direction, int nOpCode, int nOpCodeEx, boolean nEnable) {
        //log.info("traceOpcode: nEnable[{}] direction[{}] OpCode[{}:{}]", nEnable, direction, nOpCode, nOpCodeEx);
        if (direction == Direction.clientgame.value) {
            if (nOpCode == clientgame_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx)) return;
                m_aTracedOpcodeEx_CS[nOpCodeEx] = nEnable;
            } else {
                if (!isValidOpcode(direction, nOpCode)) return;
                m_aTracedOpcode_CS[nOpCode] = nEnable;
            }
        } else if (direction == gameclient.value) {
            if (nOpCode == gameclient_opcode_ex.value) {
                if (!isValidOpcodeEx(direction, nOpCodeEx)) return;
                m_aTracedOpcodeEx_SC[nOpCodeEx] = nEnable;
            } else {
                if (!isValidOpcode(direction, nOpCode)) return;
                m_aTracedOpcode_SC[nOpCode] = nEnable;
            }
        }
    }

    public boolean isValidOpcode(int direction, int nOpCode) {
        if (direction == Direction.clientgame.value) {
            return !(nOpCode < 0 || nOpCode >= m_aTracedOpcode_CS.length);
        } else if (direction == gameclient.value) {
            return !(nOpCode < 0 || nOpCode >= m_aTracedOpcode_SC.length);
        }
        return false;
    }

    public boolean isValidOpcodeEx(int direction, int nOpCodeEx) {
        if (direction == Direction.clientgame.value) {
            return !(nOpCodeEx < 0 || nOpCodeEx >= m_aTracedOpcodeEx_CS.length);
        } else if (direction == gameclient.value) {
            return !(nOpCodeEx < 0 || nOpCodeEx >= m_aTracedOpcodeEx_SC.length);
        }
        return false;
    }

    public long getGameSessionHandle() {
        return game_session_handle_;
    }

    public boolean isTryReconnect() {
        return try_reconnect_;
    }

    public void setTryReconnect(boolean onOff) {
        this.try_reconnect_ = onOff;
    }

    static public int getOpcodeEx(int direction) {
        return (direction == gameclient.value ? gameclient_opcode_ex.value : clientgame_opcode_ex.value);
    }
}