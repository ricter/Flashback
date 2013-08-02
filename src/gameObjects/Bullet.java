package gameObjects;

import graphics.Sprite;

import java.util.ArrayList;

import physics.Physics;
import processing.core.PApplet;
import processing.core.PVector;

public class Bullet extends GameObject {

    private static final float MAX_BULLET_DISTANCE = 2000;
    private static final float STARTING_VELOCITY = 1800;
    private static final float MAX_ACCURACY_ANGLE = 100;
    
	private float distanceTraveled = 0;

	/** Default monster bullet constructor*/
	Bullet(PApplet gameScreen, float x, float y, Sprite img, float targetX, float targetY) {

        this(gameScreen, x, y, img, targetX, targetY, 0.0);

        this.listOfCollideableObjects = new ArrayList<ArrayList<? extends GameObject>>();
        this.listOfCollideableObjects.add(Physics.getPlayerEntities());
        
    }
	
	/** Default player bullet constructor */
	Bullet(PApplet gameScreen, float x, float y, Sprite img, float targetX, float targetY, double heartBeat) {

		super(gameScreen, x, y, img);
		
		PVector firingVector = new PVector(targetX - x, targetY - y);
		firingVector.normalize();

		float theta = (gameScreen.random(-1, 1) + gameScreen.random(-1, 1) + gameScreen.random(-1, 1)) / 3
				* (float)heartBeat * 15;

		gameScreen.constrain(theta, -MAX_ACCURACY_ANGLE, MAX_ACCURACY_ANGLE);

		firingVector.rotate(gameScreen.radians(theta));
		this.xVelocity = firingVector.x * STARTING_VELOCITY;
		this.yVelocity = firingVector.y * STARTING_VELOCITY;
		
		this.listOfCollideableObjects = new ArrayList<ArrayList<? extends GameObject>>();
		this.listOfCollideableObjects.add(Physics.getGameEntities());
		
	}

	/** Updates all variables of the bullet when needed */
	public void update(float deltaT) {

	    computeVelocity(deltaT);
	    updateMovement(deltaT);
		distanceTraveled += gameScreen.abs(this.xVelocity*deltaT) + gameScreen.abs(this.yVelocity*deltaT);

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