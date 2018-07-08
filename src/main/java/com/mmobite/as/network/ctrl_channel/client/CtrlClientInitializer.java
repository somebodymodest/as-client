package com.mmobite.as.network.ctrl_channel.client;

import com.mmobite.as.network.client.AntiSpamClientProperties;
import com.mmobite.as.network.client.PacketDecoder;
import com.mmobite.as.network.client.PacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class CtrlClientInitializer extends ChannelInitializer<SocketChannel> {

    private final CtrlClientHandler handler_;

    public CtrlClientInitializer(CtrlClientHandler handler) {
        this.handler_ = handler;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        if (AntiSpamClientProperties.READ_TIMEOUT > 0) {
            pipeline.addLast(new IdleStateHandler(0, 0, AntiSpamClientProperties.READ_TIMEOUT), handler_);
        }

        // Add the number codec first,
        pipeline.addLast(new PacketDecoder());
        pipeline.addLast(new PacketEncoder());

        // and then business logic.
        pipeline.addLast(handler_);
    }
}
