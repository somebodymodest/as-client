package com.mmobite.as.network.data_channel.client;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.data_channel.packets.DataPacketsManager;
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
public class DataClientHandler extends SimpleChannelInboundHandler<Object> {

    private static Logger log = LoggerFactory.getLogger(DataClientHandler.class.getName());

    private DataClient client_;
    long startTime = -1;

    public void setClient(DataClient client) {
        client_ = client;
    }

    public DataClient getClient() {
        return client_;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (startTime < 0) {
            startTime = System.currentTimeMillis();
        }
        log.info("Connected to: " + ctx.channel().remoteAddress());

        client_.setChannel(ctx);
        client_.sendVersionPacket();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        short opcode = (short) buf.readByte();

        ReceiveDummyPacket pkt = DataPacketsManager.getPacket(opcode);
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
        client_.setChannel(null);
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                log.debug("Reconnecting to: " + client_.HOST_ + ':' + client_.PORT_);
                client_.tryConnect();
            }
        }, ClientProperties.RECONNECT_TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
