package com.mmobite.as.network.ctrl_channel.packets;

public class SC_Opcodes {

    /*
        format: "c"
        c - opcode
        */
    public static final short dummypacket = 0x00;

    /*
    format: "c"
    c - opcode
    */
    public static final short pingpacket = 0x01;

    /*
    format: "c"
    c - opcode
    */
    public static final short pongpacket = 0x02;

    public static final short _max = 0x03;

}
