package gameObjects;

import graphics.Sprite;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class FlyingMonster extends GameObject {

	private float targetX;
	private float targetY;

	private float fireTimer;
	private float minFireRate = 1; // 1.5
	private float maxFireRate = 4;

	private float maxSpeed = 120;

	public FlyingMonster(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		targetX = getxPos();
		targetY = yPos;

		fireTimer = 0;
		flip = false;

	}

	public void update(float deltaT) {

		if (targetX - 2 <= getxPos() && targetX + 2 >= getxPos()) {

			targetX = Physics.getPlayerEntities().get(0).getxPos()
					+ gameScreen.random(-300, 300);
			targetY = Physics.getPlayerEntities().get(0).yPos + 290;

		} // end if

		setxPos(getxPos() + ((maxSpeed * deltaT) * (getxPos() < targetX ? 1 : -1)));

		flip = getxPos() < targetX ? true : false;

		if (fireTimer < 0) {

			fireTimer = gameScreen.random(minFireRate, maxFireRate);

			Physics.addEnemyBullet(new Bullet( gameScreen,
					(float) (getxPos() + sprite.getImg().width * 0.5),
					(float) (yPos + sprite.getImg().height * 0.5),
					Flashback.bulletSprite, (float) (Physics.getPlayerEntities()
							.get(0).getxPos()
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