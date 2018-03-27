package com.mmobite.as.api.model;

public enum PacketEx {
    clientgame_opcode_ex(0xD0), gameclient_opcode_ex(0xFE);

    public final int value;

    PacketEx(int value) {
        this.value = value;
    }
}
