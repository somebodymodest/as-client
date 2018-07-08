package com.mmobite.as.network.client;

import com.mmobite.as.network.packet.WritePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ITcpClient {

    private final AtomicReference<Channel> channelRef_ = new AtomicReference<>();

    abstract public EventLoopGroup getLoop();

    public Channel getChannel() {
        return channelRef_.get();
    }

    public void setChannel(Channel channel) {
        this.channelRef_.set(channel);
    }

    public void sendPacket(final WritePacket pkt) {
        Channel ch = getChannel();
        if (ch == null || !ch.isActive() || !ch.isWritable())
            return;

        ch.writeAndFlush(pkt).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    future.cause().printStackTrace();
                }
                pkt.releaseBuffer();
            }
        });
    }
}
