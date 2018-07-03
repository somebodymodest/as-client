package com.mmobite.as.network.packet;

/**
 * Created by Ivan on 22.03.2018.
 *
 */
public interface IWritePacket extends INetPacket {
    /**
     * add payload to buffer
     */
    void writeBody();
}
