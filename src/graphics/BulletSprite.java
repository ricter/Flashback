package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class BulletSprite extends Sprite {
	
    static PImage staticImage;
    
	public BulletSprite() {
		super(staticImage);
	}

	public BulletSprite(int width, int height, int xOffset, int yOffset) {
		super(staticImage, width, height, xOffset, yOffset);
	}
	
	public static void loadImages(){
		staticImage = gameScreen.loadImage("../images/Bullet.png");
	}
	
	@Override
    public PImage getStaticImage() {
        return staticImage;
    }

    @Override
    public ArrayList<PImage> getMovingImages() {
        // TODO Auto-generated method stub
        return null;
    }
	
}