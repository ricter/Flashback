package graphics;

import processing.core.PImage;

public class BulletSprite extends Sprite {

	private static PImage bulletImage;
	
	public BulletSprite() {
		super(bulletImage);
	}

	public BulletSprite(int width, int height, int xOffset, int yOffset) {
		super(bulletImage, width, height, xOffset, yOffset);
	}
	
	public static void loadImages(){
		
		bulletImage = gameScreen.loadImage("../images/Bullet.png");
		
	}

	@Override
	public void draw(float x, float y) {

		gameScreen.image(currentImage, x, y);
		
	}

	@Override
	public void draw(float x, float y, boolean flip) {
		
		if (flip) {

			gameScreen.pushMatrix();
			gameScreen.translate(x + currentImage.width, y);
			//scale(-1.0, 1.0);
			gameScreen.scale(-1, 1);
			gameScreen.image(currentImage, 0, 0);
			gameScreen.popMatrix();
			// scale(-1.0,1.0);

		} else {
			gameScreen.image(currentImage, x, y);
		}
		
	}
	
}
