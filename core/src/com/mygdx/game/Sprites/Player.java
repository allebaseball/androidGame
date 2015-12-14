package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;


public class Player extends Sprite {
    private Texture red = new Texture("sprites/redRekt.png");
    private Texture yell = new Texture("sprites/yellRekt.png");
    private Texture green = new Texture("sprites/greenRekt.png");

    // switchPlayer variables
    private int pNum = 1;
    private int switchCount;
    private boolean switched = false;

    // update variables
    private boolean leftMove = false;
    private boolean rightMove = false;
    private boolean jumpMove = false;
    private boolean first = false;
    private boolean grounded = false;

    // player variables
    private TiledMapTileLayer collisionLayer;
    private Vector2 velocity = new Vector2();

    // world variables
    private float speed = 200*2, gravity = 100*1.8f;



    public Player(Vector2 position, TiledMapTileLayer collisionLayer) {
        super(new Texture("sprites/redRekt.png"));
        this.collisionLayer = collisionLayer;
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

        float oldX = getX(), oldY = getY();

        setX(oldX + velocity.x * dt);
        setY(oldY + velocity.y * dt);

        if (checkCollisionX()) {
            setX(oldX);
            velocity.x = 0;
        }
        if (checkCollisionY()) {
            setY(oldY);
            velocity.y = 0;
        }
    }

    private boolean checkCollisionX() {
        boolean collided = false;

        if (velocity.x < 0) {
            // top left
            collided = collisionLayer.getCell(
                    (int) (getX() / collisionLayer.getTileWidth()),
                    (int) ((getY() + getHeight()) / collisionLayer.getTileHeight())
                    ).getTile().getProperties().containsKey("blocked");

            // middle left
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) (getX() / collisionLayer.getTileWidth()),
                        (int) ((getY() + getHeight() / 2) / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");

            // bottom left
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) (getX() / collisionLayer.getTileWidth()),
                        (int) (getY() / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");
        }
        else if (velocity.x > 0) {
            // top right
            collided = collisionLayer.getCell(
                    (int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
                    (int) ((getY() + getHeight()) / collisionLayer.getTileHeight())
                    ).getTile().getProperties().containsKey("blocked");

            // middle right
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
                        (int) ((getY() + getHeight() / 2) / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");

            // bottom right
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
                        (int) (getY() / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");
        }

        return collided;
    }

    private boolean checkCollisionY() {
        boolean collided = false;

        if (velocity.y > 0) {
            // top left
            collided = collisionLayer.getCell(
                    (int) (getX() / collisionLayer.getTileWidth()),
                    (int) ((getY() + getHeight()) / collisionLayer.getTileHeight())
                    ).getTile().getProperties().containsKey("blocked");

            // top middle
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) ((getX() + getWidth() / 2) / collisionLayer.getTileWidth()),
                        (int) ((getY() + getHeight()) / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");

            // top right
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
                        (int) (getY() / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");
        }
        else if (velocity.y < 0) {
            // bottom left
            collided = collisionLayer.getCell(
                    (int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
                    (int) ((getY() + getHeight()) / collisionLayer.getTileHeight())
                    ).getTile().getProperties().containsKey("blocked");

            // bottom middle
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
                        (int) ((getY() + getHeight() / 2) / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");

            // bottom right
            if (!collided)
                collided = collisionLayer.getCell(
                        (int) ((getX() + getWidth()) / collisionLayer.getTileWidth()),
                        (int) ((getY() + getHeight()) / collisionLayer.getTileHeight())
                        ).getTile().getProperties().containsKey("blocked");
        }

        return collided;
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
            velocity.y = 500;//jumpMove = true;
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

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

}
