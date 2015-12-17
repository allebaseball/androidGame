package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Const;


public class Player extends Sprite {
    // texture def
    private Texture red = new Texture("sprites/redRekt.png");
    private Texture yell = new Texture("sprites/yellRekt.png");
    private Texture green = new Texture("sprites/greenRekt.png");

    // tile size
    private int tileWidth = Const.TILE_WIDTH;
    private int tileHeight = Const.TILE_HEIGHT;

    // motion variables
    private boolean grounded;
    private boolean leftMove = false;
    private boolean rightMove = false;
    private boolean jumpMove = false;

    // player variables
    public TiledMapTileLayer collisionLayer;
    private Vector2 velocity = new Vector2();

    // world variables
    public float speedX = Const.SPEED_X;
    public float speedY = Const.SPEED_Y;
    public float gravity = Const.GRAVITY;

    // animation
    public enum State { FALLING, JUMPING, STANDING, RUNNING }
    public State currentState;
    public State previousState;
    private TextureRegion playerStand;
    private Animation playerRun;
    private Animation playerJump;
    private float stateTimer;
    private boolean runningRight;

    public Player(TiledMapTileLayer collisionLayer) {
        super(new Texture("sprites/little_mario.png"));
        this.collisionLayer = collisionLayer;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        // getting run frames
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        playerRun = new Animation(0.1f, frames);
        frames.clear();

        // getting jump frames
        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        playerJump = new Animation(0.1f, frames);
        frames.clear();

        playerStand = new TextureRegion(getTexture(), 0, 0, 16, 16);

        setBounds(0, 0, 30, 32);
        setRegion(playerStand);
    }

    public void update (float dt) {
        // apply gravity
        velocity.y -= gravity*dt;
        updateMove();

        velocity.y = MathUtils.clamp(velocity.y, -speedY, speedY);

        float oldX = getX(), oldY = getY();
        setX(oldX + velocity.x * dt);

        if (checkCollisionX(getX(), getY())) {
            setX(oldX);
           // velocity.x = 0;
        }

        setY(oldY + velocity.y * dt);

        if (checkCollisionY(getX(), getY())) {
            setY(oldY);
            velocity.y = 0;
        }

        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState  = getState();

        TextureRegion region;

        switch (currentState) {
            case JUMPING:
                region = playerJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                /* Falls through */
            case STANDING:
                /* Falls through */
            default:
                region = playerStand;
                break;
        }

        if ((velocity.x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((velocity.x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {
        if (velocity.y > 0 || (velocity.y < 0 && previousState == State.JUMPING)) {
            return State.JUMPING;
        }
        else if (velocity.y < 0) {
            return State.FALLING;
        }
        else if (velocity.x != 0) {
            return State.RUNNING;
        }
        else
            return State.STANDING;
    }

    public void moveLeft() {
        leftMove = true;
    }

    public void moveRight() {
        rightMove = true;
    }

    public void jump() {
        jumpMove = true;
    }

    public void notMoveLeft() {
        leftMove = false;

        if(!rightMove)
            velocity.x = 0;
        else
            velocity.x = speedX;
    }

    public void notMoveRight() {
        rightMove = false;

        if(!leftMove)
            velocity.x = 0;
        else
            velocity.x = -speedX;
    }

    public void notJump() {
        jumpMove = false;
    }

    private void updateMove() {
        if (rightMove)
            velocity.x = speedX;
        else if (leftMove)
            velocity.x = -speedX;

        if (grounded && jumpMove) {
            velocity.y = speedY;
            grounded = false;
        }
    }

    private boolean checkCollisionX(float x, float y) {
        boolean collided = false;

        if (velocity.x < 0) {
            // top left
            if(collisionLayer.getCell((int) (x  / tileWidth),(int) ((y + getHeight()) / tileHeight)) != null) {
                collided = collisionLayer.getCell(
                        (int) (x / tileWidth),
                        (int) ((y + getHeight()) / tileHeight)
                        ).getTile().getProperties().containsKey("blocked");
            }

            // middle left
            if (!collided)
                if(collisionLayer.getCell((int) (x / tileWidth),(int) ((y + getHeight() / 2) / tileHeight)) != null) {
                    collided = collisionLayer.getCell(
                            (int) (x / tileWidth),
                            (int) ((y + getHeight() / 2) / tileHeight)
                             ).getTile().getProperties().containsKey("blocked");
                }

            // bottom left
            if (!collided)
                if(collisionLayer.getCell((int) (x / tileWidth),(int) (y / tileHeight)) != null) {
                    collided = collisionLayer.getCell(
                            (int) (x / tileWidth),
                            (int) (y / tileHeight)
                          ).getTile().getProperties().containsKey("blocked");
                }
        }
        else if (velocity.x > 0) {
            // top right
            if(collisionLayer.getCell((int) ((x + getWidth()) / tileWidth),(int) ((y + getHeight()) / tileHeight)) != null) {
                collided = collisionLayer.getCell(
                        (int) ((x + getWidth()) / tileWidth),
                        (int) ((y + getHeight()) / tileHeight)
                         ).getTile().getProperties().containsKey("blocked");
            }

            // middle right
            if (!collided)
                if(collisionLayer.getCell((int) ((x + getWidth()) / tileWidth),(int) ((y + getHeight() / 2) / tileHeight)) != null) {
                    collided = collisionLayer.getCell(
                            (int) ((x + getWidth()) / tileWidth),
                            (int) ((y + getHeight() / 2) / tileHeight)
                              ).getTile().getProperties().containsKey("blocked");
                }

            // bottom right
            if (!collided)
                if(collisionLayer.getCell((int) ((x + getWidth()) / tileWidth),(int) (y / tileHeight)) != null) {
                    collided = collisionLayer.getCell(
                            (int) ((x + getWidth()) / tileWidth),
                            (int) (y / tileHeight)
                            ).getTile().getProperties().containsKey("blocked");
                }
        }

        return collided;
    }

    private boolean checkCollisionY(float x, float y) {
        boolean collided = false;

        if (velocity.y > 0) {
            // top left
            if(collisionLayer.getCell((int) (x / tileWidth),(int) ((y + getHeight()) / tileHeight)) != null) {
                collided = collisionLayer.getCell(
                        (int) (x / tileWidth),
                        (int) ((y + getHeight()) / tileHeight)
                        ).getTile().getProperties().containsKey("blocked");
            }

            // top middle
            if (!collided)
                if(collisionLayer.getCell((int) ((x + getWidth() / 2) / tileWidth),(int) ((y + getHeight()) / tileHeight)) != null) {
                    collided = collisionLayer.getCell(
                            (int) ((x + getWidth() / 2) / tileWidth),
                            (int) ((y + getHeight()) / tileHeight)
                            ).getTile().getProperties().containsKey("blocked");
                }

            // top right
            if(collisionLayer.getCell((int) ((x + getWidth()) / tileWidth),(int) ((y + getHeight()) / tileHeight)) != null) {
                collided = collisionLayer.getCell(
                        (int) ((x + getWidth()) / tileWidth),
                        (int) ((y + getHeight()) / tileHeight)
                ).getTile().getProperties().containsKey("blocked");
            }
        }
        else if (velocity.y < 0) {
            // bottom left
            if(collisionLayer.getCell((int) (x / tileWidth),(int) (y / tileHeight)) != null) {
                collided = collisionLayer.getCell(
                        (int) (x / tileWidth),
                        (int) (y / tileHeight)
                       ).getTile().getProperties().containsKey("blocked");
            }
            // bottom middle
            if (!collided)
                if(collisionLayer.getCell((int) ((x + getWidth() / 2) / tileWidth),(int) (y / tileHeight)) != null) {
                    collided = collisionLayer.getCell(
                            (int) ((x + getWidth() /2 ) / tileWidth),
                            (int) (y / tileHeight)
                            ).getTile().getProperties().containsKey("blocked");
                }
            // bottom right
            if (!collided)
                if(collisionLayer.getCell((int) ((x + getWidth()) / tileWidth),(int) (y / tileHeight)) != null) {
                    collided = collisionLayer.getCell(
                            (int) ((x + getWidth()) / tileWidth),
                            (int) (y / tileHeight)
                            ).getTile().getProperties().containsKey("blocked");
                }
            grounded = collided;
        }

        return collided;
    }

    public Vector2 getPosition() {
        return new Vector2(getX(),getY());
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public State getCurrentState() {
        return currentState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public boolean isRunningRight() {
        return runningRight;
    }

    public void setPosition(Vector2 position) {
        setX(position.x);
        setY(position.y);
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public void setStateTimer(float stateTimer) {
        this.stateTimer = stateTimer;
    }

    public void setRunningRight(boolean runningRight) {
        this.runningRight = runningRight;
    }

    public void resetMoves() {
        leftMove = false;
        rightMove = false;
        jumpMove = false;
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }
}