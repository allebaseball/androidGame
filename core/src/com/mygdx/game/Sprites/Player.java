package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Const;

public abstract class Player extends Sprite {
    // tile size
    protected int tileWidth = Const.TILE_WIDTH;
    protected int tileHeight = Const.TILE_HEIGHT;

    // motion variables
    protected boolean grounded;
    protected boolean leftMove = false;
    protected boolean rightMove = false;
    protected boolean jumpMove = false;

    // player variables
    protected TiledMapTileLayer collisionLayer;
    protected Vector2 velocity = new Vector2();

    //hitbox
    protected Rectangle bottom, left, right, top;

    // world variables
    protected float speedX = Const.SPEED_X;
    protected float speedY = Const.SPEED_Y;
    protected float gravity = Const.GRAVITY;

    // animation
    public enum State { FALLING, JUMPING, STANDING, RUNNING }
    protected State currentState;
    protected State previousState;
    protected TextureRegion playerStand;
    protected Animation playerRun;
    protected Animation playerJump;
    protected float stateTimer;
    protected boolean runningRight;

    public int currentPlayer;

    public Player(TiledMapTileLayer collisionLayer, String playerTexture) {
        super(new Texture(playerTexture));



    }

    public void update (float dt) {
        // apply gravity
        velocity.y -= gravity*dt;
        this.updateMove();

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

    protected void updateMove() {
        if (rightMove)
            velocity.x = speedX;
        else if (leftMove)
            velocity.x = -speedX;

        if (grounded && jumpMove) {
            jumpMove = false;
            velocity.y = speedY;
            grounded = false;
        }
        else jumpMove = false;
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

    private boolean checkCollisionX(float x, float y) {
        boolean collided = false;

        if (velocity.x < 0) {
            // top left
                collided = isCellBlocked(x , y + getHeight());

            // middle left
            if (!collided)
                    collided = isCellBlocked(x, y + getHeight() / 2);

            // bottom left
            if (!collided)
                    collided = isCellBlocked(x, y);
        }
        else if (velocity.x > 0) {
            // top right
            collided = isCellBlocked(x + getWidth() , y + getHeight());

            // middle right
            if (!collided)
                collided = isCellBlocked(x + getWidth() , y + getHeight() / 2);

            // bottom right
            if (!collided)
                collided = isCellBlocked(x + getWidth() , y);
        }

        return collided;
    }

    private boolean checkCollisionY(float x, float y) {
        boolean collided = false;

        if (velocity.y > 0) {
            // top left
            collided = isCellBlocked(x , y + getHeight());

            // top middle
            if (!collided)
                collided = isCellBlocked(x + getWidth() / 2 , y + getHeight());

            // top right
            if(!collided)
                collided = isCellBlocked(x + getWidth() , y + getHeight());

        }
        else if (velocity.y < 0) {
            // bottom left
            collided = isCellBlocked(x , y);

            // bottom middle
            if (!collided)
                collided = isCellBlocked(x + getWidth() / 2, y);

            // bottom right
            if (!collided)
                collided = isCellBlocked(x + getWidth(), y);

            grounded = collided;
        }

        return collided;
    }

    private boolean isCellBlocked(float x, float y){
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(
                (int) ((x) / collisionLayer.getTileWidth() ),
                (int) ((y) / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");

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