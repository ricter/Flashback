package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class EyeSprite extends Sprite {

	private static ArrayList<PImage> eyeImages;
	private int imageNumber = 0;
	
	public EyeSprite() {
		super(eyeImages.get(0));
	}

	public EyeSprite(int width, int height, int xOffset, int yOffset) {
		super(eyeImages.get(0), width, height, xOffset, yOffset);
	}
	
	public static void loadImages(){
		
		eyeImages = new ArrayList<PImage>();
		eyeImages.add(gameScreen.loadImage("../images/eye1.png"));
		eyeImages.add(gameScreen.loadImage("../images/eye2.png"));
		eyeImages.add(gameScreen.loadImage("../images/eye3.png"));
		eyeImages.add(gameScreen.loadImage("../images/eye4.png"));
		
	}

	@Override
	public void draw(float x, float y) {

		gameScreen.image(currentImage, x, y);
		
		imageNumber++;
		if (imageNumber == eyeImages.size()){
			imageNumber = 0;
		}
		System.out.println(imageNumber);
		currentImage = eyeImages.get(imageNumber);
		
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
		
		imageNumber++;
		if (imageNumber == eyeImages.size()){
			imageNumber = 0;
		}
		currentImage = eyeImages.get(imageNumber);
		
	}

}
