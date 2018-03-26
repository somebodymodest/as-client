package com.mmobite.as.network.ctrl_channel.packets;

public class CS_Opcodes {

    /*
    format: "c"
    c - opcode
    */
    public static final short dummypacket = 0x00;

    /*
    format: "chhdssds"
    c - opcode
    h - nTraceProtocolId
    h - nWorldId
    d - nGameProtocolId
    s - sLicenseUserName[EMAIL_SIZE]
    s - sLicenseEmail[EMAIL_SIZE]
    d - nWithNemo
    s - sWorldGUID[DB_WORLD_GUID_SIZE]
    */
    public static final short versionpacket = 0x01;

    /*
    format: "c"
    c - opcode
    */
    public static final short pingpacket = 0x02;

    /*
    format: "c"
    c - opcode
    */
    public static final short pongpacket = 0x03;

    public static final short _max = 0x04;

}
