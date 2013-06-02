package graphics;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Sprite {

	static PApplet gameScreen;

	public static PApplet getGameScreen() {
		return gameScreen;
	}
	
	public static void setGameScreen(PApplet gameScreen) {
		Sprite.gameScreen = gameScreen;
	}
	
	PImage currentImage;
	private int collisionXOffset;
	private int collisionYOffset;
	
	private int collisionWidth;

	private int collisionHeight;
	
	public Sprite(PImage img) {
		
		this.currentImage = img;

		collisionXOffset = 0;
		collisionYOffset = 0;

		collisionWidth = img.width;
		collisionHeight = img.height;

	}

	public Sprite(PImage img, int width, int height, int xOffset, int yOffset) {
		
		this.currentImage = img;

		collisionXOffset = xOffset;
		collisionYOffset = yOffset;

		collisionWidth = width;
		collisionHeight = height;

	}

	public abstract void draw(float x, float y);

	public abstract void draw(float x, float y, boolean flip);

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

	public PImage getCurrentImage() {
		return currentImage;
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

	public void setCurrentImage(PImage img) {
		this.currentImage = img;
	}

}