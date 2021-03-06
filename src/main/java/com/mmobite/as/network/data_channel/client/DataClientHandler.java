package com.mmobite.as.network.data_channel.client;

import com.mmobite.as.network.client.AntiSpamClientProperties;
import com.mmobite.as.network.data_channel.handlers.SendVersionPacket;
import com.mmobite.as.network.data_channel.packets.DataPacketsManager;
import com.mmobite.as.network.packet.ReadPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class DataClientHandler extends ChannelInboundHandlerAdapter {

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
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;

        int opcode = (int) buf.readUnsignedByte();

        ReadPacket pkt = DataPacketsManager.getPacket(opcode);
        pkt.setBuffer(buf);

        try {
            if (pkt.read())
                pkt.run(getClient());
        } catch (Exception e){
        }

        pkt.releaseBuffer();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (!(evt instanceof IdleStateEvent)) {
            return;
        }

        IdleStateEvent e = (IdleStateEvent) evt;
        if (e.state() == IdleState.READER_IDLE) {
            // The connection was OK but there was no traffic for last period.
            log.info("Disconnecting due to no inbound traffic");
            getClient().tryReconnect(false);
            ctx.close();
        }
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        log.info("Disconnected from: " + ctx.channel().remoteAddress());
        getClient().setChannel(null);
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) {
        if (!getClient().tryReconnect())
            return;

        getClient().getLoop().schedule(new Runnable() {
            @Override
            public void run() {
                //log.info("Reconnecting to: " + getClient().host_ + ':' + getClient().port_);
                getClient().connect();
            }
        }, AntiSpamClientProperties.RECONNECT_TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //cause.printStackTrace();
        ctx.close();
    }

}
