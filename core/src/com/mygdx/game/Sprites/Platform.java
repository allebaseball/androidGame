package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Const;

public class Platform extends Sprite {
    public boolean verse = true; //true = right, false = left;
    public float spawnX;
    public float movWidth;
    public float velocity = Const.SPEED_X;

    public Platform(String platformTexture, float spawnX, float spawnY, float movWidth) {
        super(new Texture(platformTexture));
        setPosition(spawnX,spawnY);
        this.spawnX = spawnX;
        this.movWidth = movWidth;
    }

    public void update(float dt, Rectangle r) {
        float oldX = getX();
        if(verse) {
            setX(oldX + velocity * dt);
            if(getX() > spawnX + movWidth) {
                setX(oldX);
                verse = false;
            }
        }
        else {
            setX(oldX - velocity * dt);
            if(getX() < spawnX) {
                setX(oldX);
                verse = true;
            }
        }

        if(getBoundingRectangle().overlaps(r)) {
            Gdx.app.log("State", "Collided");
        }
    }
}
