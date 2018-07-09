package com.mmobite.as.network.ctrl_channel.client;

import com.mmobite.as.network.client.AntiSpamClientProperties;
import com.mmobite.as.network.client.ITcpClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class CtrlClient extends ITcpClient{

    private final static Logger log = LoggerFactory.getLogger(CtrlClient.class.getName());
    private final static EventLoopGroup loop_ = new NioEventLoopGroup();

    private final CtrlClientHandler handler_ = new CtrlClientHandler();
    private final Bootstrap bs_;
    public final String host_;
    public final int port_;
    public final int L2ProtocolVersion_;
    private final AtomicBoolean is_connected_ = new AtomicBoolean(false);

    public CtrlClient(int L2ProtocolVersion) {
        host_ = AntiSpamClientProperties.SERVER_ADDR;
        port_ = AntiSpamClientProperties.PORT_CTRL;
        L2ProtocolVersion_ = L2ProtocolVersion;

        bs_ = new Bootstrap();
        bs_.group(loop_)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(host_, port_)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new CtrlClientInitializer(handler_));

        handler_.setClient(this);

        connect();
    }

    void connect() {
        if (AntiSpamClientProperties.ENABLED) {
            //log.info("Try to connect to: " + host_ + ':' + port_);
            bs_.connect();
        } else {
            //log.info("Try to connect to: wait for timeout");
            loop_.schedule(new Runnable() {
                @Override
                public void run() {
                    connect();
                }
            }, AntiSpamClientProperties.RECONNECT_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    public EventLoopGroup getLoop() {
        return loop_;
    }

    public void setConnected(boolean onOff) {
        is_connected_.set(onOff);
    }

    public boolean isConnected() {
        return is_connected_.get();
    }
}
