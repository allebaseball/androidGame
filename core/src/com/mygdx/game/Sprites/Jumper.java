package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Const;

public class Jumper extends Player {
    protected int jumpCount = 0;

    public Jumper(TiledMapTileLayer collisionLayer) {
        super(collisionLayer, Const.PLAYER2_PATH);

        this.collisionLayer = collisionLayer;


        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        // getting run frames
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16 * 2));
        }
        playerRun = new Animation(Const.ANIMATION_TIME, frames);
        frames.clear();

        // getting jump frames
        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16 * 2));
        }
        playerJump = new Animation(0.1f, frames);
        frames.clear();

        playerStand = new TextureRegion(getTexture(), 0, 0, 16, 16 * 2);

        setBounds(0, 0, 30, 32 * 2);
        setRegion(playerStand);
    }

    @Override
    protected void updateMove() {
        if (grounded) jumpCount = 0;

        if (rightMove)
            velocity.x = speedX;
        else if (leftMove)
            velocity.x = -speedX;

        if ((grounded || jumpCount < 2) && jumpMove) {
            Gdx.app.log("Ciao", jumpCount + "Jumped" + grounded);
            jumpCount++;
            jumpMove = false;
            velocity.y = speedY;
            grounded = false;
        }
        else jumpMove = false;
    }
}