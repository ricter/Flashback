package gameObjects;

import graphics.PlayerSprite;
import graphics.Sprite;
import main.Flashback;
import physics.Physics;
import processing.core.PApplet;

public class Player extends GameObject{

	private boolean goLeft = false;
	private boolean goRight = false;
	private boolean goUp = false;
	private boolean goDown = false;

	float bps = 1.0f;
	double heartbeatTimer = 2; //2.0
	double fireRate = 0.7f;
	double lastFired = 0.0;

	double yVelocity = 0.0;
	double xSpeed = 5.0;

	public Player(PApplet gameScreen, float x, float y, Sprite sprite) {

		super(gameScreen, x, y, sprite);
		radius = 20;
		heartbeatTimer = bps;

	}

	public void update(float deltaT) {

		// calculate heart rate
		bps = (float) (1.0 + Physics.gameEntities.size());

		if (bps > 8){
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

	public void tryToFire(){
		
		if (gameScreen.millis() - lastFired > fireRate) { // fire bullet

			lastFired = gameScreen.millis();
			Physics.addPlayerBullet( new Bullet(gameScreen, (float)(getxPos() + sprite.getCurrentImage().width* 0.5), 
					(float) (yPos + sprite.getCurrentImage().height * 0.5),
					Flashback.bulletSprite, gameScreen.mouseX + Flashback.levelData.getxDistanceFromLeftWall(),
					gameScreen.mouseY,
					bps));

		}
		
	}
	
	public void draw(int x, int y) {

		sprite.draw(x + getxPos(), y + yPos, flip);
		// line(x+xPos+sprite.img.width *0.5,y+yPos+ sprite.img.height
		// *0.5,mouseX, mouseY);
		/*
		 * if(heartbeatTimer < 1.0) { //draw heart stroke(214,13,13);
		 * fill(214,13,13); rect(xResolution - 20, 10, 5, 5);
		 * 
		 * text(bps * 50, xResolution - 300, 40); }
		 */

	}
	
	private void tryToJump(float deltaT){
	
		if(Physics.isAtGround(getxPos(), yPos, sprite.getCollisionHeight())){
			yVelocity = 200;
		}
	
	}
	
	public void OnCollision(){
	
		//Flashback.hit.rewind();
		//Flashback.hit.play();
		fireRate -= 0.01;
		fireRate = fireRate < 0 ? 0 : fireRate; 
	
	}

	public boolean isGoUp() {
		return goUp;
	}

	public void setGoUp(boolean goUp) {
		this.goUp = goUp;
	}

	public boolean isGoLeft() {
		return goLeft;
	}

	public void setGoLeft(boolean goLeft) {
		this.goLeft = goLeft;
	}

	public boolean isGoDown() {
		return goDown;
	}

	public void setGoDown(boolean goDown) {
		this.goDown = goDown;
	}

	public boolean isGoRight() {
		return goRight;
	}

	public void setGoRight(boolean goRight) {
		this.goRight = goRight;
	}
	
}