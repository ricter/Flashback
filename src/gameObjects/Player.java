package gameObjects;

import graphics.PlayerArmSprite;
import graphics.PlayerSprite;
import graphics.Sprite;

import java.util.ArrayList;

import main.Flashback;
import physics.Physics;
import processing.core.PApplet;
import processing.core.PVector;


public class Player extends GameObject {

	private static final double maxFireRateAdjustment = 0.5;
	private static final double MS_TO_S = 1000;
	
	protected PlayerArmSprite armSprite;
	private float armXOffset = 40;
    private float armYOffset = 36;
	
	private boolean goLeft = false;
	private boolean goRight = false;
	private boolean goUp = false;
	private boolean goDown = false;
	private boolean tryToFire = false;
	private boolean hasLanded = true;

	private double startingBPS = 1.0;
    private double currentBaseBPS = startingBPS;
    private double heartbeatTimer = 2; //2.0
	private double maxBPS = 4.0;
	private double minBPS = 0.20;
	private double manualHeartRateAdjustment = 0.0;
	private double damageHeartRateAdjustment = 0.0;
	
    private float lastFired = 0;    
    
	private PVector bulletSpawnPosition;
	
	private Scorecard scorecard;
	
	public Player(PApplet gameScreen, float x, float y, Sprite sprite, PlayerArmSprite armSprite) {

		super(gameScreen, x, y, sprite);
		
		heartbeatTimer = currentBaseBPS;
		this.armSprite = armSprite;
		this.armSprite.setxOffset(armXOffset);
		this.armSprite.setyOffset(armYOffset);
		bulletSpawnPosition = new PVector();
		
		scorecard = new Scorecard();
		
		this.isAffectedByGravity = true;
		this.listOfCollideableObjects = new ArrayList<ArrayList<? extends GameObject>>();
		this.listOfCollideableObjects.add(Physics.getWalls());
		
	}

    public void draw(int x, int y) {

		sprite.draw(x + getxPosition(), y + yPosition, flipImage);
		armSprite.draw(x + getxPosition(), y + yPosition, flipImage);
		gameScreen.fill(255, 0, 0);
		gameScreen.text("BPS: "+ getCurrentTotalBPS() + " FRA:" + manualHeartRateAdjustment, 10, 40);

	}
	
	public double getFireRateAdjustment() {
		return manualHeartRateAdjustment;
	}
	
	public Scorecard getScorecard() {
        return scorecard;
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
	
	public boolean isTryToFire() {
        return tryToFire;
    }

	public void OnCollision(){
	
		Flashback.hit.rewind();
		Flashback.hit.play();
		damageHeartRateAdjustment -= .125;
		
	}
	
	public void setBulletSpawnPosition(PVector position) {
		
		this.bulletSpawnPosition = position;
		
	}
	
	public void setFireRateAdjustment(double heartRateAdjustment) {
		
		if (heartRateAdjustment > maxFireRateAdjustment){
			this.manualHeartRateAdjustment = maxFireRateAdjustment;
		} else if (heartRateAdjustment < -maxFireRateAdjustment){
			this.manualHeartRateAdjustment = -maxFireRateAdjustment;
		} else this.manualHeartRateAdjustment = heartRateAdjustment;
		
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

	public void setTryToFire(boolean tryToFire) {
        this.tryToFire = tryToFire;
    }

	public void tryToFire(){
		
		double adjustedFireRate = adjustFireRate();
        if (gameScreen.millis() - lastFired > adjustedFireRate) { // fire bullet

            //System.out.println("gameScreen.millis():" + gameScreen.millis() + " lastFired:" + lastFired + " adjustedFireRate:" + adjustedFireRate);
            lastFired = gameScreen.millis();
            Physics.addPlayerBullet(new Bullet(gameScreen,
                                               getxPosition() - Flashback.xResolution / 2 + this.bulletSpawnPosition.x,
                                               this.bulletSpawnPosition.y,
                                               Flashback.bulletSprite,
                                               gameScreen.mouseX + Flashback.levelData.getxDistanceFromLeftWall(),
                                               gameScreen.mouseY,
                                               getCurrentTotalBPS()));
            ((PlayerArmSprite) this.armSprite).setFiring(true);
			
		} else {
			
			// Player cannot fire yet, do nothing
			//System.out.println("Can't fire yet.");
		    
		}
		
	}

	public void update(float deltaT) {

		updateHeartRate();

		if (isGoLeft()) {

			flipImage = true;
			xAcceleration = -maxManualXAcceleration;

		} else if (isGoRight()) {

			flipImage = false;
			xAcceleration = maxManualXAcceleration;

		} else {
			
			((PlayerSprite) this.sprite).setRunning(false);
			xAcceleration = 0;
			xVelocity = 0;
			
		}

		if (isGoUp()) {

			tryToJump(deltaT);

		} else if (isGoDown()) {

		    // re-add ability to go down
		    
		}

		computeVelocity(deltaT);
        updateMovement(deltaT);
		
		if (tryToFire){
		    tryToFire();
		}
		
		/*
		 * heartbeatTimer -= bps*deltaT; if(heartbeatTimer < 0) { heartbeatTimer
		 * = 2.0; if(dub != null) { lub.rewind(); lub.play(); } } else if(
		 * heartbeatTimer < 1 && heartbeatTimer > .95) { if(lub != null) {
		 * dub.rewind(); dub.play(); } }
		 */

	}

    private void updateHeartRate() {
        
        // calculate heart rate
		currentBaseBPS = (float) (startingBPS + Physics.getGameEntities().size()/4);

		if (getCurrentTotalBPS() > maxBPS || getCurrentTotalBPS() < minBPS){
			Flashback.loseScreen.setLoseScreenActive(true);
		}

		adjustMaxAccelerationAndMaxVelocity();
		
    }
	
	private double adjustFireRate() {
		
		double adjustedFireRate = (1/getCurrentTotalBPS()) * MS_TO_S;
		
		return adjustedFireRate;
		
	}
	
    private void adjustMaxAccelerationAndMaxVelocity() {
        
        maxXVelocity = (float) (getCurrentTotalBPS() * 32 + 80);
        maxManualXAcceleration = (float) (getCurrentTotalBPS() * 32 + 80);
        
    }

	private double getCurrentTotalBPS(){
	    
	    double currentTotalBPS = currentBaseBPS + manualHeartRateAdjustment + damageHeartRateAdjustment;
	    if (currentTotalBPS <= 0){
	        currentTotalBPS = 0.1;
	    }
	    return currentTotalBPS;
	    
	}

	private void tryToJump(float deltaT){
	
		if(hasLanded && yVelocity < .5){
			yAcceleration = -1750;
			hasLanded = false;
		}
	
	}
	
	@Override
	public void triggerYCollision(float oldY){
        
	    hasLanded= true;
	    ((PlayerSprite)sprite).setJumping(false);
        yVelocity = 0;
        yAcceleration = 0;
        yPosition = oldY;
        
    }
	
}