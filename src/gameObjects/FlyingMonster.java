package gameObjects;

import graphics.Sprite;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class FlyingMonster extends GameObject {

	float targetX;
	float targetY;

	float fireTimer;
	float minFireRate = 1; // 1.5
	float maxFireRate = 4;

	float maxSpeed = 120;

	public FlyingMonster(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		targetX = xPos;
		targetY = yPos;

		fireTimer = 0;
		flip = false;

	}

	public void update(float deltaT) {

		if (targetX - 2 <= xPos && targetX + 2 >= xPos) {

			targetX = Physics.getPlayerEntities().get(0).xPos
					+ gameScreen.random(-300, 300);
			targetY = Physics.getPlayerEntities().get(0).yPos + 290;

		} // end if

		xPos += ((maxSpeed * deltaT) * (xPos < targetX ? 1 : -1));

		flip = xPos < targetX ? true : false;

		if (fireTimer < 0) {

			fireTimer = gameScreen.random(minFireRate, maxFireRate);

			Physics.addEnemyBullet(new Bullet( gameScreen,
					(float) (xPos + sprite.getImg().width * 0.5),
					(float) (yPos + sprite.getImg().height * 0.5),
					Flashback.bulletSprite, (float) (Physics.getPlayerEntities()
							.get(0).xPos
							+ Physics.getPlayerEntities().get(0).sprite
									.getImg().width * 0.5), 
									(float) (Physics
							.getPlayerEntities().get(0).yPos
							+ Physics.getPlayerEntities().get(0).sprite
									.getImg().height * 0.5)));

		} // end if
		fireTimer -= deltaT;

	} // end update

}