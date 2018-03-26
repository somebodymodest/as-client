package com.mmobite.as.network.packet;

/**
 * Created by Ivan on 22.03.2018.
 *
 */
public interface IReadPacket extends INetPacket {
    boolean read();
    void run();
}

