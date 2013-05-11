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
		targetX = xPos;
		targetY = yPos;

		fireTimer = 0;

	}

	public void update(float deltaT) {

		if (targetX - 2 <= xPos && targetX + 2 >= xPos) {

			targetX = Physics.getPlayerEntities().get(0).xPos + gameScreen.random(-300, 300);
			targetY = Physics.getPlayerEntities().get(0).yPos + 290;

		}

		xPos += ((maxSpeed * deltaT) * (xPos < targetX ? 1 : -1));

		flip = xPos < targetX ? true : false;

		yVelocity = Physics.applyGravity(yVelocity, deltaT);
		yPos = Physics.stopAtGround(xPos, yPos, -(float) yVelocity * deltaT,
				sprite.getCollisionHeight());
		if (Physics.isAtGround(xPos, yPos, sprite.getCollisionHeight()))
			yVelocity = 0;

		if (fireTimer < 0) {

			fireTimer = gameScreen.random(minFireRate, maxFireRate);

			Physics.addEnemyBullet(new Bullet(gameScreen, xPos, yPos, Flashback.bulletSprite,
					Physics.getPlayerEntities().get(0).xPos, Physics.getPlayerEntities()
							.get(0).yPos));

		}
		fireTimer -= deltaT;

	}

}
