package com.ernestas.entities;

public class Player extends AliveEntity {

    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_POSITION_X = 0;
    public static final int DEFAULT_POSITION_Y = 0;

    public Player() {
        super(DEFAULT_HEALTH);
        setX(DEFAULT_POSITION_X);
        setY(DEFAULT_POSITION_Y);
    }

}
