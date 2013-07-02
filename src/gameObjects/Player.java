package gameObjects;

import graphics.PlayerArmSprite;
import graphics.PlayerSprite;
import graphics.Sprite;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;


public class Player extends GameObject{

	private static final double maxFireRateAdjustment = 2.0;
	
	protected Sprite armSprite;
	
	private boolean goLeft = false;
	private boolean goRight = false;
	private boolean goUp = false;
	private boolean goDown = false;

	private double bps = 1.0;
	private double heartbeatTimer = 2; //2.0
	private double fireRate = 0.7;
	private double fireRateAdjustment = 0.0;
	private double lastFired = 0.0;

	private double yVelocity = 0.0;
	private double xSpeed = 5.0;
	
	private float armXOffset = 30;
	private float armYOffset = 50;
	
	public Player(PApplet gameScreen, float x, float y, Sprite sprite, Sprite armSprite) {

		super(gameScreen, x, y, sprite);
		radius = 20;
		heartbeatTimer = bps;
		this.armSprite = armSprite;

	}
	
	public void draw(int x, int y) {

		sprite.draw(x + getxPos(), y + yPos, flip);
		armSprite.draw(x + getxPos() + armXOffset, y + yPos + armYOffset, flip);
		// line(x+xPos+sprite.img.width *0.5,y+yPos+ sprite.img.height
		// *0.5,mouseX, mouseY);
		/*
		 * if(heartbeatTimer < 1.0) { //draw heart stroke(214,13,13);
		 * fill(214,13,13); rect(xResolution - 20, 10, 5, 5);
		 * 
		 * text(bps * 50, xResolution - 300, 40); }
		 */

	}

	public double getFireRateAdjustment() {
		return fireRateAdjustment;
	}

	public boolean isGoDown() {
		return goDown;
	}

	public boolean isGoLeft() {
		return goLeft;
	}
	
	public boolean isGoRight() {
		return goRight;
	}

	public boolean isGoUp() {
		return goUp;
	}
	
	public void OnCollision(){
	
		Flashback.hit.rewind();
		Flashback.hit.play();
		fireRate -= 0.01;
		fireRate = fireRate < 0 ? 0 : fireRate; 
	
	}
	
	public void setFireRateAdjustment(double fireRateAdjustment) {
		
		if (fireRateAdjustment > 2.0){
			this.fireRateAdjustment = 2.0;
		} else if (fireRateAdjustment < -2.0){
			this.fireRateAdjustment = -2.0;
		} else this.fireRateAdjustment = fireRateAdjustment;
		
	}

	public void setGoDown(boolean goDown) {
		this.goDown = goDown;
	}

	public void setGoLeft(boolean goLeft) {
		this.goLeft = goLeft;
	}

	public void setGoRight(boolean goRight) {
		this.goRight = goRight;
	}

	public void setGoUp(boolean goUp) {
		this.goUp = goUp;
	}

	public void tryToFire(){
		
		if (gameScreen.millis() - lastFired > adjustFireRate()) { // fire bullet

			lastFired = gameScreen.millis();
			Physics.addPlayerBullet( new Bullet(gameScreen, (float)(getxPos() + sprite.getCurrentImage().width* 0.5), 
					(float) (yPos + sprite.getCurrentImage().height * 0.5),
					Flashback.bulletSprite, gameScreen.mouseX + Flashback.levelData.getxDistanceFromLeftWall(),
					gameScreen.mouseY,
					bps));
			((PlayerArmSprite) this.armSprite).setFiring(true);
			
		} else {
			
			((PlayerArmSprite) this.armSprite).setFiring(false);
			
		}
		
	}

	public void update(float deltaT) {

		// calculate heart rate
		bps = (float) (1.0 + Physics.gameEntities.size());

		if (bps > 8 || bps <= 0){
			Flashback.loseScreen.setLoseScreenActive(true);
		}

		xSpeed = 5.0 * (bps / 10 + 1);

		if (isGoLeft()) {

			flip = true;
			setxPos(Physics.stopAtWall(getxPos() - 10, sprite.getCollisionWidth(),
					sprite.getCollisionHeight()));

		} else if (isGoRight()) {

			flip = false;
			setxPos(Physics.stopAtWall(getxPos() + 10, sprite.getCollisionWidth(),
					sprite.getCollisionHeight()));

		} else {
			
			((PlayerSprite) this.sprite).setRunning(false);
			
		}

		if (isGoUp()) {

			tryToJump(deltaT);

		} else if (isGoDown()) {

			if (Physics.isAtGround(getxPos(), yPos, sprite.getCollisionHeight())
					&& !(yPos == Flashback.yResolution - sprite.getCollisionHeight())) {

				yPos += 2;

			}
		}

		yVelocity = Physics.applyGravity(yVelocity, deltaT);
		yPos = Physics.stopAtGround(getxPos(), yPos, -(float) yVelocity * deltaT,
				sprite.getCollisionHeight());
		if (Physics.isAtGround(getxPos(), yPos, sprite.getCollisionHeight())){
			yVelocity = 0;
			((PlayerSprite) this.sprite).setJumping(false);
		}
			
		/*
		 * heartbeatTimer -= bps*deltaT; if(heartbeatTimer < 0) { heartbeatTimer
		 * = 2.0; if(dub != null) { lub.rewind(); lub.play(); } } else if(
		 * heartbeatTimer < 1 && heartbeatTimer > .95) { if(lub != null) {
		 * dub.rewind(); dub.play(); } }
		 */

	}

	private double adjustFireRate() {
		
		double limitedBPS = bps + fireRateAdjustment;
		if (limitedBPS > 6){
			limitedBPS = 6;
		} else if (limitedBPS < 2) {
			limitedBPS = 2;
		}
		
		double adjustedFireRate = fireRate;
		
		return adjustedFireRate;
		
	}

	private void tryToJump(float deltaT){
	
		if(Physics.isAtGround(getxPos(), yPos, sprite.getCollisionHeight())){
			yVelocity = 200;
		}
	
	}
	
}