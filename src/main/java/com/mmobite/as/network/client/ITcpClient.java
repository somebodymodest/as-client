package com.mmobite.as.network.client;

import com.mmobite.as.network.packet.WritePacket;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public abstract class ITcpClient {

    protected final Logger log = LoggerFactory.getLogger(getClass());

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
            ch.writeAndFlush(pkt);
    }
}
