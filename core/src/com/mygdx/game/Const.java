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
    public static final String LEFTARROW_PATH = "sprites/left_arrow.png";
    public static final String RIGHTARROW_PATH = "sprites/right_arrow.png";
    public static final String JOYSTICK_BACK_PATH = "sprites/Joystick/Joystick_background.png";
    public static final String JOYSTICK_KNOB_PATH = "sprites/Joystick/Joystick_knob.png";
    public static final String MENU_START_PATH = "sprites/MainMenu/Menu_start.png";
    public static final String MENU_SETTINGS_PATH = "sprites/MainMenu/Menu_settings.png";
    public static final String MENU_QUIT_PATH = "sprites/MainMenu/Menu_quit.png";

    // controller variables
    public static final int STICK_NONE = 0;
    public static final int STICK_UP = 1;
    public static final int STICK_RIGHT = 2;
    public static final int STICK_DOWN = 3;
    public static final int STICK_LEFT = 4;

    private Const() {
        throw new AssertionError();
    }
}
