package com.mmobite.as.network.ctrl_channel.client;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.ctrl_channel.packets.CtrlPacketsManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CtrlTcpClient {

    private static Logger log = LoggerFactory.getLogger(CtrlTcpClient.class.getName());

    private final static EventLoopGroup loop_ = new NioEventLoopGroup();
    private final static CtrlTcpClientHandler handler_ = new CtrlTcpClientHandler();
    private final Bootstrap bs_;
    public final String HOST_;
    public final int PORT_;
    public final int L2ProtocolVersion_;

    public CtrlTcpClient(int L2ProtocolVersion) {
        HOST_ = ClientProperties.SERVER_ADDR;
        PORT_ = ClientProperties.PORT_CTRL;
        L2ProtocolVersion_ = L2ProtocolVersion;

        bs_ = new Bootstrap();
        bs_.group(loop_)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(HOST_, PORT_)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new CtrlTcpClientInitializer(handler_));

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
}
