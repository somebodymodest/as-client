package com.mmobite.as.network.ctrl_channel.client;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.ctrl_channel.handlers.SendVersionPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

public class CtrlClient {

    private static Logger log = LoggerFactory.getLogger(CtrlClient.class.getName());

    private final static EventLoopGroup loop_ = new NioEventLoopGroup();
    private final static CtrlClientHandler handler_ = new CtrlClientHandler();
    private final Bootstrap bs_;
    public final String HOST_;
    public final int PORT_;
    public final int L2ProtocolVersion_;
    private ChannelHandlerContext ctx_;
    private final AtomicBoolean is_connected_ = new AtomicBoolean(false);

    public CtrlClient(int L2ProtocolVersion) {
        HOST_ = ClientProperties.SERVER_ADDR;
        PORT_ = ClientProperties.PORT_CTRL;
        L2ProtocolVersion_ = L2ProtocolVersion;

        bs_ = new Bootstrap();
        bs_.group(loop_)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(HOST_, PORT_)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new CtrlClientInitializer(handler_));

        handler_.setClient(this);

        bs_.connect();
    }

    void connect() {
        bs_.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.cause() != null) {
                    handler_.startTime = -1;
                    log.error("Failed to connect: " + future.cause());
                }
            }
        });
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

    public void setConnected(boolean onOff) {
        is_connected_.set(onOff);
    }

    public boolean getConnected() {
        return is_connected_.get();
    }
}