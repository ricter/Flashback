package graphics;

import processing.core.PApplet;
import processing.core.PImage;

public class ZombieSprite extends Sprite {

	private static PImage zombieImage;
	
	public ZombieSprite() {
		super(zombieImage);
	}

	public ZombieSprite(PApplet gameScreen, int width, int height, int xOffset, int yOffset) {
		super(zombieImage, width, height, xOffset, yOffset);
	}
	
	public static void loadImages(){
		
		zombieImage = gameScreen.loadImage("../images/zombie.png");
		
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