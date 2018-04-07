package com.mmobite.as.network.client;

import com.mmobite.as.network.packet.WritePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ITcpClient {

    private static Logger log = LoggerFactory.getLogger(ITcpClient.class.getName());

    private final AtomicReference<Channel> channelRef = new AtomicReference<>();

    abstract public EventLoopGroup getLoop();

    public Channel getChannel() {
        return channelRef.get();
    }

    public void setChannel(Channel channel) {
        this.channelRef.set(channel);
    }

    public void sendPacket(final WritePacket pkt) {
        Channel ch = getChannel();
        if (ch != null)
            ch.writeAndFlush(pkt).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) {
                    if (!future.isSuccess()) {
                        log.info("Something wrong while sending packet to AntiSpam server:");
                        future.cause().printStackTrace();
                    }
                    pkt.getBuffer().release();
                }
            });
    }
}
