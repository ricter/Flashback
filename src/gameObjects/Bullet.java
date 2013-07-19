package gameObjects;

import graphics.Sprite;
import processing.core.PApplet;
import processing.core.PVector;

public class Bullet extends GameObject {

	private float vel = 15;
	private PVector vec;
	private float distanceTraveled = 0;
	private float maxDistance = 2000;

	// Test to remove
	Bullet(PApplet gameScreen, float x, float y, Sprite img, float targetX, float targetY) {

		super(gameScreen, x, y, img);
		vec = new PVector(targetX - x, targetY - y);
		vec.normalize();

	}

	// Default constructor
	Bullet(PApplet gameScreen, float x, float y, Sprite img, float targetX, float targetY, double heartBeat) {

		super(gameScreen, x, y, img);
		vec = new PVector(targetX - x, targetY - y);
		vec.normalize();

		float theta = (gameScreen.random(-1, 1) + gameScreen.random(-1, 1) + gameScreen.random(-1, 1)) / 3
				* (float)heartBeat * 10;

		gameScreen.constrain(theta, -100, 100);

		vec.rotate(gameScreen.radians(theta));

	}

	// updates all variables of the bullet when needed
	public void update(float deltaT) {

		setXPosition(getXPosition() + vec.x * vel);
		yPosition = yPosition + vec.y * vel;
		distanceTraveled += gameScreen.abs(vec.x * vel) + gameScreen.abs(vec.y * vel);

	}

	public void draw(int x, int y) {

		gameScreen.strokeWeight(1);
		gameScreen.stroke(240, 7, 42);
		gameScreen.fill(240, 7, 42);
		gameScreen.ellipse(x + getXPosition(), y + yPosition, 5, 5);

	}

	// Return true if a bullet should be removed (traveled too far, etc)
	public boolean shouldRemove() {
		return distanceTraveled > maxDistance || removeMe;
	}

}
