package com.mmobite.as.network.ctrl_channel.client;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.ctrl_channel.handlers.SendVersionPacket;
import com.mmobite.as.network.ctrl_channel.packets.CtrlPacketsManager;
import com.mmobite.as.network.packet.ReceiveDummyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class CtrlTcpClientHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger log = LoggerFactory.getLogger(CtrlTcpClientHandler.class.getName());

    private CtrlTcpClient client_;
    long startTime = -1;

    public void setClient(CtrlTcpClient client) {
        client_ = client;
    }

    public CtrlTcpClient getClient() {
        return client_;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (startTime < 0) {
            startTime = System.currentTimeMillis();
        }
        log.info("Connected to: " + ctx.channel().remoteAddress());

        getClient().setChannel(ctx);
        getClient().sendVersionPacket();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        short opcode = (short) buf.readByte();

        ReceiveDummyPacket pkt = CtrlPacketsManager.getPacket(opcode);
        pkt.setOpcode(opcode);
        pkt.setBuffer(buf);
        pkt.setChannel(ctx);

        if (pkt.read())
            pkt.run();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }

        IdleStateEvent e = (IdleStateEvent) evt;
        if (e.state() == IdleState.READER_IDLE) {
            // The connection was OK but there was no traffic for last period.
            log.debug("Disconnecting due to no inbound traffic");
            ctx.close();
        }
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        log.debug("Disconnected from: " + ctx.channel().remoteAddress());
        getClient().setChannel(null);
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        log.debug("Sleeping for: " + ClientProperties.RECONNECT_TIMEOUT + 's');

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                log.debug("Reconnecting to: " + client_.HOST_ + ':' + client_.PORT_);
                client_.connect();
            }
        }, ClientProperties.RECONNECT_TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
