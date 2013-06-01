package gameObjects;

import graphics.Sprite;
import physics.Physics;
import processing.core.PApplet;

public class Monster extends GameObject {

	private boolean goLeft = false;
	private boolean goRight = false;
	private boolean goUp = false;
	private boolean goDown = false;

	protected double yVelocity = 0.0;

	private float maxSpeed = 70;

	public Monster(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		radius = 20;

	}

	public void update(float deltaT) {

		yVelocity = Physics.applyGravity(yVelocity, deltaT);
		yPos = Physics.stopAtGround(getxPos(), yPos, -(float) yVelocity * deltaT,
				sprite.getCollisionHeight());
		if (Physics.isAtGround(getxPos(), yPos, sprite.getCollisionHeight()))
			yVelocity = 0;

	}

}
