package com.mygdx.game;

public class Const {
    // screen size
    public static final int V_WIDTH = 416 * 16 / 9;
    public static final int V_HEIGHT = 416;

    // tile size
    public static final int TILE_WIDTH = 32;
    public static final int TILE_HEIGHT = 32;

    // world variables
    public static final float GRAVITY = 220*1.8f;
    public static final float SPEED_X = 50*2;
    public static final float SPEED_Y = 120*2;

    // animation variables
    public static final float ANIMATION_TIME = 0.1f;

    // STRINGS
    public static final String MAP_PATH = "maps/TestTiledMapTest.tmx";
    // texture strings
    public static final String RED_PATH = "sprites/redRekt.png";
    public static final String YELL_PATH = "sprites/yellRekt.png";
    public static final String GREEN_PATH = "sprites/greenRekt.png";
    public static final String PLAYER1_PATH = "sprites/little_mario.png";
    public static final String PLAYER2_PATH = "sprites/big_mario.png";

    private Const() {
        throw new AssertionError();
    }
}
