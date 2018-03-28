package com.mmobite.as.network.packet;

import com.mmobite.as.network.client.ITcpClient;

/**
 * Created by Ivan on 22.03.2018.
 *
 */
public interface IReadPacket extends INetPacket {

    boolean read();
    void run(ITcpClient client);
}

