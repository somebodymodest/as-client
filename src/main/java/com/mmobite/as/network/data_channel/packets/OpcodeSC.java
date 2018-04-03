package com.mmobite.as.network.data_channel.packets;

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

	/*
	format: "c"
		c - opcode
	*/
    public static final int stoptracepacket = 0x03;

	/*
	format: "c"
		c - opcode
	*/
    public static final int starttracepacket = 0x04;

	/*
	format: "ccch"
		c - opcode
		c - nDirection (clientgame = 6, gameclient = 7)
		c - opcode
		h - opcode_ex
	*/
    public static final int stoptraceopcodepacket	= 0x05;

	/*
	format: "ccch"
		c - opcode
		c - nDirection (clientgame = 6, gameclient = 7)
		c - opcode
		h - opcode_ex
	*/
    public static final int starttraceopcodepacket = 0x06;

    public static final int _max = 0x07;

}
