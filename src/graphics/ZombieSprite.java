package graphics;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class ZombieSprite extends Sprite {
	
    static PImage staticImage;
    
	public ZombieSprite() {
		super(staticImage);
	}

	public ZombieSprite(PApplet gameScreen, int width, int height, int xOffset, int yOffset) {
		super(staticImage, width, height, xOffset, yOffset);
	}
	
	public static void loadImages(){
		
	    staticImage = gameScreen.loadImage("../images/zombie.png");
		
	}

    @Override
    public PImage getStaticImage() {
        return staticImage;
    }

    @Override
    public ArrayList<PImage> getMovingImages() {
        return null;
    }
	
}