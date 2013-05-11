package gameObjects;

import graphics.Sprite;
import processing.core.PApplet;

public class GameObject {

	PApplet gameScreen;
	
	public float xPos;
	public float yPos;

	float radius;

	boolean flip;
	boolean removeMe = false;

	Sprite sprite;

	public GameObject(PApplet gameScreen) {

		this.gameScreen = gameScreen;
		xPos = 0;
		yPos = 0;
		this.sprite = null;

	}

	public GameObject(PApplet gameScreen, float x, float y, Sprite sprite) {

		this.gameScreen = gameScreen;
		xPos = x;
		yPos = y;
		this.sprite = sprite;

	}

	public void draw(int x, int y) {

		if (sprite != null) {
			sprite.draw(xPos + x, yPos + y, flip);
		}
		// image(sprite.img, xPos+x, yPos+y);

	}

	// Should never be called, should be overloaded by any children
	public void update(float deltaT) {

	}

	public boolean collide(GameObject other) {

		if (sprite != null && other.sprite != null) {

			float left1, left2;
			float right1, right2;
			float top1, top2;
			float bottom1, bottom2;

			left1 = xPos + sprite.getCollisionXOffset();
			left2 = other.xPos + other.sprite.getCollisionXOffset();
			right1 = xPos + sprite.getCollisionXOffset() + sprite.getCollisionWidth();
			right2 = other.xPos + other.sprite.getCollisionXOffset() + other.sprite.getCollisionWidth();
			top1 = yPos + sprite.getCollisionYOffset();
			top2 = other.yPos + other.sprite.getCollisionYOffset();
			bottom1 = yPos + sprite.getCollisionYOffset() + sprite.getCollisionHeight();
			bottom2 = other.yPos + other.sprite.getCollisionYOffset() + other.sprite.getCollisionWidth();

			if (flip) {
				left1 += sprite.getCollisionWidth();
				right1 += sprite.getCollisionWidth();
			}
			/*
			 * if(other.flip) { right2 -= other.sprite.collisionWidth; left2 -=
			 * other.sprite.collisionWidth; }
			 */

			if (bottom1 < top2)
				return false;
			if (top1 > bottom2)
				return false;

			if (right1 < left2)
				return false;
			if (left1 > right2)
				return false;

			return true;

		} else {

			// circle circle collision for now
			float distX = other.xPos - other.xPos;
			float distY = other.yPos - other.yPos;

			return gameScreen.sqrt(distX * distX + distY * distY) <= radius + other.radius;

		}

	}

	public boolean shouldRemove() {

		// println(distanceTraveled);
		return removeMe;

	}

	public void animateDeath() {

		removeMe = true;

	}

}