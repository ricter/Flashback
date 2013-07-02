package graphics;

import java.util.ArrayList;

import processing.core.PImage;

public class PlayerArmSprite extends Sprite {

	private static ArrayList<PImage> runImages;

	private static PImage idleImage;
	private static PImage firingImage;

	public static void loadImages() {

		idleImage = gameScreen.loadImage("../images/commandoPlayerGun.png");
		firingImage = gameScreen.loadImage("../images/commandoPlayerGunFire.png");

	}

	private boolean isFiring = false;

	private double angle = 0;

	public PlayerArmSprite() {
		super(idleImage);
	}

	public PlayerArmSprite(int width, int height, int xOffset, int yOffset) {
		super(idleImage, width, height, xOffset, yOffset);
	}

	@Override
	public void draw(float x, float y) {

		gameScreen.image(currentImage, x, y);

	}

	@Override
	public void draw(float x, float y, boolean flip) {

		if (isFiring) {

			this.setCurrentImage(firingImage);

		} else {

			this.setCurrentImage(idleImage);

		}

		if (flip) {

			gameScreen.pushMatrix();
			gameScreen.translate(x + currentImage.width, y);
			// scale(-1.0, 1.0);
			gameScreen.scale(-1, 1);
			gameScreen.image(currentImage, 0, 0);
			gameScreen.popMatrix();
			// scale(-1.0,1.0);

		} else {
			gameScreen.image(currentImage, x, y);
		}

	}

	public double getAngle() {
		return angle;
	}

	public boolean isFiring() {
		return isFiring;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public void setFiring(boolean isFiring) {
		this.isFiring = isFiring;
	}

}