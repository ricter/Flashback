package graphics;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Sprite {

	static PApplet gameScreen;
	
	PImage currentImage;
	
    private int collisionXOffset;
    private int collisionYOffset;
    private int collisionWidth;
    private int collisionHeight;
    
    boolean movingImage;
    private int imageNumber = 0;
    private int fpsCount = 0;
    private int animationSpeed = 5;
    
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
	
	public abstract PImage getStaticImage();
	public abstract ArrayList<PImage> getMovingImages();
	
	public static PApplet getGameScreen() {
		return gameScreen;
	}

	public static void setGameScreen(PApplet gameScreen) {
		Sprite.gameScreen = gameScreen;
	}

    public void draw(float x, float y) {

        gameScreen.image(currentImage, x, y);
        
        if(fpsCount++ > animationSpeed) {
            fpsCount = 0;
            imageNumber++;
            if (imageNumber == getMovingImages().size()){
                imageNumber = 0;
            }
            currentImage = getMovingImages().get(imageNumber);
        }
        
    }

    public void draw(float x, float y, boolean flip) {
        
        if (flip) {

            gameScreen.pushMatrix();
            gameScreen.translate(x + currentImage.width, y);
            gameScreen.scale(-1, 1);
            gameScreen.image(currentImage, 0, 0);
            gameScreen.popMatrix();

        } else {
            gameScreen.image(currentImage, x, y);
        }
        
        if (movingImage){
            
            imageNumber++;
            if (imageNumber == getMovingImages().size()){
                imageNumber = 0;
            }
            currentImage = getMovingImages().get(imageNumber);
            
        }
        
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