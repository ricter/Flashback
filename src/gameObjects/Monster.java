package gameObjects;

import graphics.Sprite;
import physics.Physics;
import processing.core.PApplet;

public class Monster extends GameObject {

	boolean goLeft = false;
	boolean goRight = false;
	boolean goUp = false;
	boolean goDown = false;

	double yVelocity = 0.0;

	float maxSpeed = 70;

	public Monster(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		radius = 20;

	}

	public void update(float deltaT) {

		yVelocity = Physics.applyGravity(yVelocity, deltaT);
		yPos = Physics.stopAtGround(xPos, yPos, -(float) yVelocity * deltaT,
				sprite.getCollisionHeight());
		if (Physics.isAtGround(xPos, yPos, sprite.getCollisionHeight()))
			yVelocity = 0;

	}

}
