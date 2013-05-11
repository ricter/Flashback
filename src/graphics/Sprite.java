package graphics;

import processing.core.PApplet;
import processing.core.PImage;

public class Sprite {

	PApplet gameScreen;
	
	PImage img;
	
	private int collisionXOffset;
	private int collisionYOffset;
	private int collisionWidth;
	private int collisionHeight;
	
	public Sprite(PApplet gameScreen, PImage img) {

		this.gameScreen = gameScreen;
		this.img = img;

		collisionXOffset = 0;
		collisionYOffset = 0;

		collisionWidth = img.width;
		collisionHeight = img.height;

	}

	public Sprite(PApplet gameScreen, PImage img, int width, int height, int xOffset, int yOffset) {

		this.gameScreen = gameScreen;
		this.img = img;

		collisionXOffset = xOffset;
		collisionYOffset = yOffset;

		collisionWidth = width;
		collisionHeight = height;

	}

	public void draw(float x, float y) {

		gameScreen.image(img, x, y);
		// stroke(40,240,48);
		// fill(40,240,48);
		// rect(x+collisionXOffset, y+collisionYOffset, collisionWidth,
		// collisionHeight);

	}

	public void draw(float x, float y, boolean flip) {

		if (flip) {

			gameScreen.pushMatrix();
			gameScreen.translate(x + img.width, y);
			//scale(-1.0, 1.0);
			gameScreen.scale(-1, 1);
			gameScreen.image(img, 0, 0);
			gameScreen.popMatrix();
			// scale(-1.0,1.0);

		} else {
			gameScreen.image(img, x, y);
		}

		/*
		 * stroke(40,240,48); fill(40,240,48); rect(x+collisionXOffset,
		 * y+collisionYOffset, collisionWidth, collisionHeight);
		 */

	}

	public int getCollisionHeight() {
		return collisionHeight;
	}

	public int getCollisionWidth() {
		return collisionWidth;
	}

	public int getCollisionXOffset() {
		return collisionXOffset;
	}

	public int getCollisionYOffset() {
		return collisionYOffset;
	}

	public PImage getImg() {
		return img;
	}

	public void setCollisionHeight(int collisionHeight) {
		this.collisionHeight = collisionHeight;
	}

	public void setCollisionWidth(int collisionWidth) {
		this.collisionWidth = collisionWidth;
	}

	public void setCollisionXOffset(int collisionXOffset) {
		this.collisionXOffset = collisionXOffset;
	}

	public void setCollisionYOffset(int collisionYOffset) {
		this.collisionYOffset = collisionYOffset;
	}

	public void setImg(PImage img) {
		this.img = img;
	}

}