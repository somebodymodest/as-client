package com.mmobite.as.network.packet;

/**
 * Created by Ivan on 22.03.2018.
 *
 */
public interface IWritePacket extends INetPacket {

    void write();
    void writeBody();
    void writeHeader();
    void sendPacket();

}
