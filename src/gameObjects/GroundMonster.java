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
		targetX = getxPos();
		targetY = yPos;

		fireTimer = 0;

	}

	public void update(float deltaT) {

		if (targetX - 2 <= getxPos() && targetX + 2 >= getxPos()) {

			targetX = Physics.getPlayerEntities().get(0).getxPos() + gameScreen.random(-300, 300);
			targetY = Physics.getPlayerEntities().get(0).yPos + 290;

		}

		setxPos(getxPos() + ((maxSpeed * deltaT) * (getxPos() < targetX ? 1 : -1)));

		flip = getxPos() < targetX ? true : false;

		yVelocity = Physics.applyGravity(yVelocity, deltaT);
		yPos = Physics.stopAtGround(getxPos(), yPos, -(float) yVelocity * deltaT,
				sprite.getCollisionHeight());
		if (Physics.isAtGround(getxPos(), yPos, sprite.getCollisionHeight()))
			yVelocity = 0;

		if (fireTimer < 0) {

			fireTimer = gameScreen.random(minFireRate, maxFireRate);

			Physics.addEnemyBullet(new Bullet(gameScreen, getxPos(), yPos, Flashback.bulletSprite,
					Physics.getPlayerEntities().get(0).getxPos(), Physics.getPlayerEntities()
							.get(0).yPos));

		}
		fireTimer -= deltaT;

	}

}
