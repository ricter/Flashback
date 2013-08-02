package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class EyeSprite extends Sprite {
	
    static PImage staticImage;
    static ArrayList<PImage> movingImages;
    
	public EyeSprite() {
		super(movingImages.get(0));
		movingImage = true;
	}

	public EyeSprite(int width, int height, int xOffset, int yOffset) {
		super(movingImages.get(0), width, height, xOffset, yOffset);
		movingImage = true;
	}
	
	public static void loadImages(){
		
	    movingImages = new ArrayList<PImage>();
		movingImages.add(gameScreen.loadImage("../images/eye1.png"));
		movingImages.add(gameScreen.loadImage("../images/eye2.png"));
		movingImages.add(gameScreen.loadImage("../images/eye3.png"));
		movingImages.add(gameScreen.loadImage("../images/eye4.png"));
		
	}

	@Override
    public PImage getStaticImage() {
        return staticImage;
    }

    @Override
    public ArrayList<PImage> getMovingImages() {
        return movingImages;
    }
	
}