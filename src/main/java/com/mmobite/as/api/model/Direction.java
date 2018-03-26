package com.mmobite.as.api.model;

public enum Direction {
    ClientGame(6), GameClient(7);

    public final int value;

    Direction(int value) {
        this.value = value;
    }
}
