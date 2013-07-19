package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class PlayerSprite extends Sprite{

	private static ArrayList<PImage> jumpImages;
	private static ArrayList<PImage> runImages;
	
	private static PImage idleImage;
	
	public static void loadImages(){
		
		idleImage = gameScreen.loadImage("../images/commando_idle.png");
		
		runImages = new ArrayList<PImage>();
		runImages.add(gameScreen.loadImage("../images/commando_run1.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run2.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run3.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run4.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run5.png"));
		runImages.add(gameScreen.loadImage("../images/commando_run6.png"));
		
		jumpImages = new ArrayList<PImage>();
		jumpImages.add(gameScreen.loadImage("../images/commando_jump_no_gun.png"));
		jumpImages.addAll(runImages);
		
	}
	
	private int jumpingImageNumber = 0;
	private int runningImageNumber = 0;
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
			
			if(fpsCount++ > animationSpeed) {
				fpsCount = 0;
				jumpingImageNumber++;
				if (jumpingImageNumber == jumpImages.size()){
					jumpingImageNumber = 0;
				}
				this.setCurrentImage(jumpImages.get(jumpingImageNumber));
			}
			
		} else if (isRunning){
			
			if(fpsCount++ > animationSpeed) {
				fpsCount = 0;
				runningImageNumber++;
				if (runningImageNumber == runImages.size()){
					runningImageNumber = 0;
				}
				this.setCurrentImage(runImages.get(runningImageNumber));
			}
			
		} else { // is idle
			
			this.setCurrentImage(idleImage);
			
		}
		
		if (flip) {

			gameScreen.pushMatrix();
			gameScreen.translate(x + currentImage.width, y);
			gameScreen.scale(-1, 1);
			gameScreen.image(currentImage, 0, 0);
			gameScreen.popMatrix();

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
		jumpingImageNumber = 0; // reset jump image to first image
		this.isJumping = isJumping;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

}
