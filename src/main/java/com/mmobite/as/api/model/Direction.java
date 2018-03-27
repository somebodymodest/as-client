package com.mmobite.as.api.model;

public enum Direction {
    clientgame(6), gameclient(7);

    public final int value;

    Direction(int value) {
        this.value = value;
    }
}
