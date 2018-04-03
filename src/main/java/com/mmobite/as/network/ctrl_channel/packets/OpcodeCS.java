package com.mmobite.as.network.ctrl_channel.packets;

public class OpcodeCS {

    /*
    format: "c"
        c - opcode
    */
    public static final int dummypacket = 0x00;

    /*
    format: "chhdssds"
        c - opcode
        h - nTraceProtocolId
        h - nWorldId
        d - nGameProtocolId
        s - sLicenseUserName[EMAIL_SIZE]
        s - sLicenseEmail[EMAIL_SIZE]
        d - server type (0-PTS, 1-Java)
        s - sWorldGUID[DB_WORLD_GUID_SIZE]
    */
    public static final int versionpacket = 0x01;

    /*
    format: "c"
        c - opcode
    */
    public static final int pingpacket = 0x02;

    /*
    format: "c"
        c - opcode
    */
    public static final int pongpacket = 0x03;

    public static final int _max = 0x04;

}
