package com.mmobite.as.network.data_channel.packets;

public class CS_Opcodes {

    /*
    format: "c"
        c - opcode
    */
    public static final int dummypacket = 0x00;

    /*
    format: "chhdbssddss"
        c - opcode
        h - nTraceProtocolId
        h - nWorldId
        d - nUserSocketObjectId
        b(4 bytes) - IP addr (in_addr struct)
        s - sAccountName[ACCOUNT_NAME_SIZE]
        s - sCharName[CHARACTER_NAME_SIZE]
        d - nCharDbId
        d - nAccountId
        s - sWorldGUID[DB_WORLD_GUID_SIZE]
        s - sHwid[DB_HWID_SIZE]
        d - player total online time
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

    /*
    format: "cdddb"
        c - opcode
        d - nDirection (clientgame = 6, gameclient = 7)
        d - nTickCount
        d - nPacketSize
        b(nPacketSize bytes) - la2-packet (first byte is opcode)
    */
    public static final int packetdatapacket = 0x04;

    /*
	format: "cs"
		c - opcode
		s - hwid[DB_HWID_SIZE]
	*/
    public static final int hwidpacket = 0x05;

    /*
	format: "csd"
		c - opcode
        s - sAccountName[ACCOUNT_NAME_SIZE]
        s - sCharName[CHARACTER_NAME_SIZE]
        d - nCharDbId
        d - nAccountId
		s - hwid[DB_HWID_SIZE]
		d - player total online time
	*/
    public static final int gamesessioninfopacket = 0x06;

    public static final int _max = 0x07;

}
