package com.mmobite.as.network.data_channel.client;

import com.mmobite.as.network.client.ClientProperties;
import com.mmobite.as.network.data_channel.handlers.SendVersionPacket;
import com.mmobite.as.network.data_channel.packets.DataPacketsManager;
import com.mmobite.as.network.data_channel.handlers.ReceiveDummyPacket;
import com.mmobite.as.network.packet.ReadPacket;
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

    public void setClient(DataClient client) {
        client_ = client;
    }

    public DataClient getClient() {
        return client_;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("Connected to: " + ctx.channel().remoteAddress());

        getClient().setChannel(ctx.channel());
        getClient().sendPacket(new SendVersionPacket(getClient()));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;

        short opcode = (short) buf.readByte();

        ReadPacket pkt = DataPacketsManager.getPacket(opcode);
        pkt.setBuffer(buf);

        if (pkt.read())
            pkt.run(getClient());
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
        if (!getClient().isTryReconnect())
            return;

        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                log.debug("Reconnecting to: " + getClient().HOST_ + ':' + getClient().PORT_);
                getClient().tryConnect();
            }
        }, ClientProperties.RECONNECT_TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
