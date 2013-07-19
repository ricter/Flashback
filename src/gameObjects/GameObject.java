package gameObjects;

import graphics.Sprite;
import processing.core.PApplet;

public class GameObject {

	protected PApplet gameScreen;
	
	private  float xPosition;
	protected  float yPosition;

	protected float radius;

	protected boolean flip;
	protected boolean removeMe = false;

	protected Sprite sprite;

	public GameObject(PApplet gameScreen) {

		this.gameScreen = gameScreen;
		setXPosition(0);
		yPosition = 0;
		this.sprite = null;

	}

	public GameObject(PApplet gameScreen, float x, float y, Sprite sprite) {

		this.gameScreen = gameScreen;
		setXPosition(x);
		yPosition = y;
		this.sprite = sprite;

	}

	public void draw(int x, int y) {

		if (sprite != null) {
			sprite.draw(getXPosition() + x, yPosition + y, flip);
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

			left1 = getXPosition() + sprite.getCollisionXOffset();
			left2 = other.getXPosition() + other.sprite.getCollisionXOffset();
			right1 = getXPosition() + sprite.getCollisionXOffset() + sprite.getCollisionWidth();
			right2 = other.getXPosition() + other.sprite.getCollisionXOffset() + other.sprite.getCollisionWidth();
			top1 = yPosition + sprite.getCollisionYOffset();
			top2 = other.yPosition + other.sprite.getCollisionYOffset();
			bottom1 = yPosition + sprite.getCollisionYOffset() + sprite.getCollisionHeight();
			bottom2 = other.yPosition + other.sprite.getCollisionYOffset() + other.sprite.getCollisionWidth();

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
			float distX = other.getXPosition() - other.getXPosition();
			float distY = other.yPosition - other.yPosition;

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

	public float getXPosition() {
		return xPosition;
	}

	public void setXPosition(float xPos) {
		this.xPosition = xPos;
	}
	
	public float getYPosition() {
        return yPosition;
    }

    public void setYPosition(float yPos) {
        this.yPosition = yPos;
    }

}