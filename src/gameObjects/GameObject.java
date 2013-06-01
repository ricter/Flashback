package gameObjects;

import graphics.Sprite;
import processing.core.PApplet;

public class GameObject {

	protected PApplet gameScreen;
	
	private  float xPos;
	protected  float yPos;

	protected float radius;

	protected boolean flip;
	protected boolean removeMe = false;

	protected Sprite sprite;

	public GameObject(PApplet gameScreen) {

		this.gameScreen = gameScreen;
		setxPos(0);
		yPos = 0;
		this.sprite = null;

	}

	public GameObject(PApplet gameScreen, float x, float y, Sprite sprite) {

		this.gameScreen = gameScreen;
		setxPos(x);
		yPos = y;
		this.sprite = sprite;

	}

	public void draw(int x, int y) {

		if (sprite != null) {
			sprite.draw(getxPos() + x, yPos + y, flip);
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

			left1 = getxPos() + sprite.getCollisionXOffset();
			left2 = other.getxPos() + other.sprite.getCollisionXOffset();
			right1 = getxPos() + sprite.getCollisionXOffset() + sprite.getCollisionWidth();
			right2 = other.getxPos() + other.sprite.getCollisionXOffset() + other.sprite.getCollisionWidth();
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
			float distX = other.getxPos() - other.getxPos();
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

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

}