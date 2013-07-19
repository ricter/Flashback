package gameObjects;

import graphics.PlayerArmSprite;
import graphics.PlayerSprite;
import graphics.Sprite;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;
import processing.core.PVector;


public class Player extends GameObject{

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

	private double startingBPS = 1.0;
    private double currentBaseBPS = startingBPS;
    private double heartbeatTimer = 2; //2.0
	private double maxBPS = 4.0;
	private double minBPS = 0.20;
	private double manualHeartRateAdjustment = 0.0;
	private double damageHeartRateAdjustment = 0.0;
	
    private float lastFired = 0;    
    
	private PVector bulletSpawnPosition;
	
	private double yVelocity = 0.0;
	private double xSpeed = 6.0;
	
	private Scorecard scorecard;
	
	public Player(PApplet gameScreen, float x, float y, Sprite sprite, PlayerArmSprite armSprite) {

		super(gameScreen, x, y, sprite);
		radius = 20;
		heartbeatTimer = currentBaseBPS;
		this.armSprite = armSprite;
		this.armSprite.setxOffset(armXOffset);
		this.armSprite.setyOffset(armYOffset);
		bulletSpawnPosition = new PVector();
		scorecard = new Scorecard();
		
	}

    public void draw(int x, int y) {

		sprite.draw(x + getXPosition(), y + yPosition, flip);
		armSprite.draw(x + getXPosition(), y + yPosition, flip);
		gameScreen.fill(255, 0, 0);
		gameScreen.text("BPS: "+ getCurrentTotalBPS() + " FRA:" + manualHeartRateAdjustment, 10, 40);
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
                                               getXPosition() - Flashback.xResolution / 2 + this.bulletSpawnPosition.x,
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

		// calculate heart rate
		currentBaseBPS = (float) (startingBPS + Physics.gameEntities.size()/4);

		if (getCurrentTotalBPS() > maxBPS || getCurrentTotalBPS() < minBPS){
			Flashback.loseScreen.setLoseScreenActive(true);
		}

		adjustSpeed();

		if (isGoLeft()) {

			flip = true;
			setXPosition(Physics.stopAtWall(getXPosition() - (float)xSpeed, sprite.getCollisionWidth(),
					sprite.getCollisionHeight()));

		} else if (isGoRight()) {

			flip = false;
			setXPosition(Physics.stopAtWall(getXPosition() + (float)xSpeed, sprite.getCollisionWidth(),
					sprite.getCollisionHeight()));

		} else {
			
			((PlayerSprite) this.sprite).setRunning(false);
			
		}

		if (isGoUp()) {

			tryToJump(deltaT);

		} else if (isGoDown()) {

			if (Physics.isAtGround(getXPosition(), yPosition, sprite.getCollisionHeight())
					&& !(yPosition == Flashback.yResolution - sprite.getCollisionHeight())) {

				yPosition += 2;

			}
		}

		yVelocity = Physics.applyGravity(yVelocity, deltaT);
		yPosition = Physics.stopAtGround(getXPosition(), yPosition, -(float) yVelocity * deltaT,
				sprite.getCollisionHeight());
		if (Physics.isAtGround(getXPosition(), yPosition, sprite.getCollisionHeight())){
			yVelocity = 0;
			((PlayerSprite) this.sprite).setJumping(false);
		}
		
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
	
	private double adjustFireRate() {
		
		double adjustedFireRate = (1/getCurrentTotalBPS()) * MS_TO_S;
		
		//System.out.println("currentBPS:" + currentBPS + " adjustedFireRate:" + adjustedFireRate + " limitedBPS:" + limitedBPS);
		
		return adjustedFireRate;
		
	}
	
    private void adjustSpeed() {
        
        xSpeed = getCurrentTotalBPS() * 1.5 + 2;
        
    }

	private double getCurrentTotalBPS(){
	    
	    double currentTotalBPS = currentBaseBPS + manualHeartRateAdjustment + damageHeartRateAdjustment;
	    if (currentTotalBPS <= 0){
	        currentTotalBPS = 0.1;
	    }
	    return currentTotalBPS;
	    
	}

	private void tryToJump(float deltaT){
	
		if(Physics.isAtGround(getXPosition(), yPosition, sprite.getCollisionHeight())){
			yVelocity = 200;
		}
	
	}
	
}