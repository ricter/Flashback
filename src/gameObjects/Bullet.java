package gameObjects;

import graphics.Sprite;
import processing.core.PApplet;


public class Bullet extends GameObject {

    protected static final float MAX_BULLET_DISTANCE = 2000;
    protected static final float STARTING_VELOCITY = 1800;
    protected static final float MAX_ACCURACY_ANGLE = 100;

    private float distanceTraveled = 0;

    public Bullet(PApplet gameScreen, float x, float y, Sprite sprite) {
        super(gameScreen, x, y, sprite);
    }

    /** Updates all variables of the bullet when needed */
    public void update(float deltaT) {

        computeVelocity(deltaT);
        updateMovement(deltaT);
        distanceTraveled += gameScreen.abs(this.xVelocity * deltaT) + gameScreen.abs(this.yVelocity * deltaT);

    }

    public void draw(int x, int y) {

        gameScreen.strokeWeight(1);
        gameScreen.stroke(240, 7, 42);
        gameScreen.fill(240, 7, 42);
        gameScreen.ellipse(x + getxPosition(), y + yPosition, 5, 5);

    }

    /** Return true if a bullet should be removed (traveled too far, etc) */
    public boolean shouldRemove() {
        return distanceTraveled > MAX_BULLET_DISTANCE || removeMe;
    }
    
}