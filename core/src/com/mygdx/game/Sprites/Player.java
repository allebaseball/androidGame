package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.InputProcessor;


public class Player extends Sprite implements InputProcessor{
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

    //handle moving variables
    private boolean canJump;

    // player variables
    private TiledMapTileLayer collisionLayer;
    private Vector2 velocity = new Vector2();

    // world variables
    private float speed = 100*2, gravity = 100*1.8f;



    public Player(TiledMapTileLayer collisionLayer) {
        super(new Texture("sprites/redRekt.png"));
        this.collisionLayer = collisionLayer;
        setBounds(0, 0, 26 , 50);
    }

    public void update (float dt) {
        // apply gravity
        velocity.y -= gravity*dt;

        // clamp velocity
//        if (velocity.y > speed)
//            velocity.y = speed;
//        else if (velocity.y < -speed)
//            velocity.y = -speed;

        velocity.y = MathUtils.clamp(velocity.y, -speed, speed);

        float oldX = getX(), oldY = getY();
        System.out.println(velocity.x +" " +  velocity.y);

        setX(oldX + velocity.x * dt);

        if (checkCollisionX(getX(), getY())) {
            setX(oldX);
            velocity.x = 0;
        }

        setY(oldY + velocity.y * dt);

        if (checkCollisionY(getX(), getY())) {
            setY(oldY);
            velocity.y = 0;
        }
    }

    private boolean checkCollisionX(float x, float y) {
        boolean collided = false;
        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

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
        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

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
            canJump = collided;
        }

        return collided;
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
    }

    public void resetSwitch() {
        switched = false;
    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    // InputProcessor methods
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                break;
            case Input.Keys.W:
                if (canJump) {
                    velocity.y = speed;
                    canJump = false;
                }
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                /* falls through */
            case Input.Keys.D:
                velocity.x = 0;
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
