package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;


public class Player extends Sprite {
    private Texture red = new Texture("sprites/redRekt.png");
    private Texture yell = new Texture("sprites/yellRekt.png");
    private Texture green = new Texture("sprites/greenRekt.png");

    //switchPlayer variables
    private int pNum = 1;
    private int switchCount;
    private boolean switched = false;

    //update variables
    private boolean leftMove = false;
    private boolean rightMove = false;
    private boolean jumpMove = false;
    private boolean first = false;
    private boolean grounded = false;

    private Vector2 velocity = new Vector2();
    private float speed = 80*2, gravity = 40*1.8f;



    public Player(Vector2 position) {
        super(new Texture("sprites/redRekt.png"));
        definePlayer();
        setBounds(0, 0, 26 , 50);
        setPosition(position.x,position.y);
    }

    public void update (float dt) {
        //apply gravity
        velocity.y -= gravity*dt;

        //clamp velocity
        if (velocity.y > speed)
            velocity.y = speed;
        else if (velocity.y < -speed)
            velocity.y = -speed;

        float oldX = getX(),oldY = getY();

        setX(oldX + velocity.x * dt);
        setY(oldY + velocity.y * dt);

        if (getY() < 32) {
            setY(oldY);
            grounded = true;
        }
    }

    public void setLeftMove(boolean flag) {
        leftMove = flag;
        first = flag;
    }

    public void setRightMove(boolean flag) {
        rightMove = flag;
        first = flag;
    }

    public void setJump() {
        if (grounded) {
            velocity.y += 500;//jumpMove = true;
            grounded = false;
        }
    }

    public void resetLeftMove() {
        leftMove = false;
    }

    public void resetRightMove() {
        rightMove = false;
    }

    public void switchPlayer(int verse) {
//        System.out.println(switched);
        System.out.println("Ciao");

            if (verse == 1)
                if (++pNum > 3) pNum = 1;
            if (verse == -1)
                if (--pNum < 1) pNum = 3;

            switch (pNum) {
                case 1:
                    setTexture(red);
                    break;
                case 2:
                    setTexture(yell);
                    break;
                case 3:
                    setTexture(green);
                    break;
                default:
                    break;
            }

//            System.out.println(++switchCount);
//        }

//        System.out.println(pNum);
    }

    public void resetSwitch() {
        switched = false;
    }

    public void definePlayer() {

    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

}
