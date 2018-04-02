package com.mmobite.as.network.client;

import com.mmobite.as.network.packet.WritePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ITcpClient {

    private static Logger log = LoggerFactory.getLogger(ITcpClient.class.getName());

    private final AtomicReference<Channel> channelRef = new AtomicReference<>();

    public Channel getChannel() {
        return channelRef.get();
    }

    public void setChannel(Channel channel) {
        this.channelRef.set(channel);
    }

    public void sendPacket(WritePacket pkt) {
        Channel ch = getChannel();
        if (ch != null)
            ch.writeAndFlush(pkt).addListener(SendExceptionHandler);
    }

    private final ChannelFutureListener SendExceptionHandler = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) {
            //log.info("operationComplete: {}", future.cause());
            if (!future.isSuccess()) {
                future.cause().printStackTrace();
            }
        }
    };
}
