package com.mmobite.as.network.ctrl_channel.packets;

public class OpcodeSC {

    /*
    format: "c"
        c - opcode
    */
    public static final int dummypacket = 0x00;

    /*
    format: "c"
        c - opcode
    */
    public static final int pingpacket = 0x01;

    /*
    format: "c"
        c - opcode
    */
    public static final int pongpacket = 0x02;

    public static final int _max = 0x03;

}
