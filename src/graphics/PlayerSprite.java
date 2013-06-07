package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class PlayerSprite extends Sprite{

	private static ArrayList<PImage> runImages;
	
	private static PImage idleImage;
	private static PImage jumpImage;
	
	public static void loadImages(){
		
		idleImage = gameScreen.loadImage("../images/commando_idle.png");
		jumpImage = gameScreen.loadImage("../images/commando_jump.png");
		
		runImages = new ArrayList<PImage>();
		runImages.add(gameScreen.loadImage("../images/commando_run1.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run2.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run3.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run4.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run5.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run6.png"));
		
	}
	
	private int imageNumber = 0;
	private static int fpsCount = 0;
	private static int animationSpeed = 5;
	
	private boolean isJumping = false;

	private boolean isRunning = false;

	public PlayerSprite() {
		super(idleImage);
	}

	public PlayerSprite(int width, int height, int xOffset, int yOffset) {
		super(idleImage, width, height, xOffset, yOffset);
	}
	@Override
	public void draw(float x, float y) {

		gameScreen.image(currentImage, x, y);
		
	}
	
	@Override
	public void draw(float x, float y, boolean flip) {
		
		if (isJumping){
			
			this.setCurrentImage(jumpImage);
			
		} else if (isRunning){
			
			if(fpsCount++ > animationSpeed) {
				fpsCount = 0;
				imageNumber++;
				if (imageNumber == runImages.size()){
					imageNumber = 0;
				}
				this.setCurrentImage(runImages.get(imageNumber));
			}
			
		} else { // is idle
			
			this.setCurrentImage(idleImage);
			
		}
		
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

	public boolean isJumping() {
		return isJumping;
	}
	
	public boolean isRunning() {
		return isRunning;
	}

	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
