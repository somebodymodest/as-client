package com.mmobite.as.network.data_channel.packets;

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

	/*
	format: "c"
		c - opcode
	*/
    public static final short stoptracepacket = 0x03;

	/*
	format: "c"
		c - opcode
	*/
    public static final short starttracepacket = 0x04;

	/*
	format: "ccch"
		c - opcode
		c - nDirection (clientgame = 6, gameclient = 7)
		c - opcode
		h - opcode_ex
	*/
    public static final short stoptraceopcodepacket	= 0x05;

	/*
	format: "ccch"
		c - opcode
		c - nDirection (clientgame = 6, gameclient = 7)
		c - opcode
		h - opcode_ex
	*/
    public static final short starttraceopcodepacket = 0x06;

    public static final short _max = 0x07;

}
