package gameObjects;

import graphics.Sprite;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class GroundMonster extends Monster {

	float targetX;
	float targetY;

	float fireTimer;
	float minFireRate = 1; //1.5
	float maxFireRate = 4;

	float maxSpeed = 120;

	public GroundMonster(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		targetX = getXPosition();
		targetY = yPosition;

		fireTimer = 0;

	}

	public void update(float deltaT) {

		if (targetX - 2 <= getXPosition() && targetX + 2 >= getXPosition()) {

			targetX = Physics.getPlayerEntities().get(0).getXPosition() + gameScreen.random(-300, 300);
			targetY = Physics.getPlayerEntities().get(0).yPosition + 290;

		}

		setXPosition(getXPosition() + ((maxSpeed * deltaT) * (getXPosition() < targetX ? 1 : -1)));

		flip = getXPosition() < targetX ? true : false;

		yVelocity = Physics.applyGravity(yVelocity, deltaT);
		yPosition = Physics.stopAtGround(getXPosition(), yPosition, -(float) yVelocity * deltaT,
				sprite.getCollisionHeight());
		if (Physics.isAtGround(getXPosition(), yPosition, sprite.getCollisionHeight()))
			yVelocity = 0;

		if (fireTimer < 0) {

			fireTimer = gameScreen.random(minFireRate, maxFireRate);

			Physics.addEnemyBullet(new Bullet(gameScreen, getXPosition(), yPosition, Flashback.bulletSprite,
					Physics.getPlayerEntities().get(0).getXPosition(), Physics.getPlayerEntities()
							.get(0).yPosition));

		}
		fireTimer -= deltaT;

	}

}
